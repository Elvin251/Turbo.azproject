const API = "http://localhost:8080";

const ROUTES = {
  register: "/api/auth/register",
  login: "/api/auth/login",
  becomeSeller: "/api/users/become-seller",

  adsActive: "/api/ads/active",
  adsMine: "/api/ads/mine",
  adsAll: "/api/ads",
  adById: (id) => `/api/ads/${id}`,
  adCreate: "/api/ads",
  adUpdate: (id) => `/api/ads/${id}`,
  adDelete: (id) => `/api/ads/${id}`,
  adStatus: (id) => `/api/ads/${id}/status`,

  brands: "/api/brands",
  brandUpdate: (id) => `/api/brands/${id}`,
  brandDelete: (id) => `/api/brands/${id}`,

  models: "/api/models",
  modelUpdate: (id) => `/api/models/${id}`,
  modelDelete: (id) => `/api/models/${id}`,

  favorites: "/api/favorites",
  favAdd: (adId) => `/api/favorites/${adId}`,
  favDelete: (adId) => `/api/favorites/${adId}`,
};

function getToken(){ return localStorage.getItem("token"); }
function setToken(t){ localStorage.setItem("token", t); }
function clearAuth(){
  localStorage.removeItem("token");
  localStorage.removeItem("roles");
  localStorage.removeItem("email");
  localStorage.removeItem("userId");
}
function getRoles(){
  try{ return JSON.parse(localStorage.getItem("roles") || "[]"); }catch(e){ return []; }
}
function isRole(r){ return getRoles().includes(r); }

function toast(msg){
  const t = document.getElementById("toast");
  if(!t){ alert(msg); return; }
  t.innerText = msg;
  t.style.display = "block";
  setTimeout(()=> t.style.display="none", 2600);
}

function unwrap(res){
  if(res && typeof res === "object" && ("success" in res) && ("data" in res)){
    if(res.success === false) throw new Error(res.message || "Xəta baş verdi");
    return res.data;
  }
  return res;
}

async function api(url, method="GET", body=null, auth=false){
  const headers = {};
  if(body) headers["Content-Type"] = "application/json";
  if(auth && getToken()) headers["Authorization"] = "Bearer " + getToken();

  const res = await fetch(API + url, {
    method,
    headers,
    body: body ? JSON.stringify(body) : null
  });

  const json = await res.json().catch(()=> ({}));
  if(!res.ok){
    throw new Error(json?.message || `HTTP ${res.status}`);
  }
  return json;
}

function imgUrl(url){
  return (url && url.trim().length) ? url : "https://picsum.photos/900/600";
}

function renderNav(){
  const nav = document.getElementById("navLinks");
  if(!nav) return;

  const logged = !!getToken();

  nav.innerHTML = `
    <a class="btn" href="index.html">Elanlar</a>
    ${logged && isRole("ROLE_BUYER") ? `<a class="btn" href="favorites.html">Seçilmişlər</a>` : ``}
    ${logged && isRole("ROLE_SELLER") ? `<a class="btn" href="my-ads.html">Mənim elanlarım</a>` : ``}
    ${logged && isRole("ROLE_ADMIN") ? `<a class="btn" href="admin.html">Admin</a>` : ``}
    ${logged && isRole("ROLE_BUYER") ? `<button class="btn ok" id="becomeSellerBtn">SELLER ol</button>` : ``}
    ${!logged
      ? `<a class="btn accent" href="login.html">Login</a><a class="btn" href="register.html">Register</a>`
      : `<button class="btn danger" id="logoutBtn">Logout</button>`
    }
  `;

  const lo = document.getElementById("logoutBtn");
  if(lo) lo.onclick = ()=>{ clearAuth(); location.href="index.html"; };

  const bs = document.getElementById("becomeSellerBtn");
  if(bs) bs.onclick = async ()=>{
    try{
      await api(ROUTES.becomeSeller, "PUT", null, true);
      toast("SELLER status verildi. Logout edib yenidən login et.");
    }catch(e){ toast(e.message); }
  };
}

function openModal(html){
  const back = document.getElementById("modalBack");
  const body = document.getElementById("modalBody");
  if(!back || !body) return;
  back.style.display="flex";
  body.innerHTML = html;

  const closeBtn = document.getElementById("modalCloseBtn");
  if(closeBtn) closeBtn.onclick = closeModal;

  back.onclick = (e)=>{ if(e.target.id==="modalBack") closeModal(); };
}

function closeModal(){
  const back = document.getElementById("modalBack");
  if(back) back.style.display="none";
}

async function pageLogin(){
  renderNav();
  loginForm.onsubmit = async (e)=>{
    e.preventDefault();
    try{
      const res = await api(ROUTES.login, "POST", {
        email: loginEmail.value.trim(),
        password: loginPassword.value.trim()
      }, false);

      const data = unwrap(res);
      setToken(data.token);
      localStorage.setItem("roles", JSON.stringify(data.roles || []));
      localStorage.setItem("email", data.email || "");
      localStorage.setItem("userId", String(data.userId || ""));
      location.href="index.html";
    }catch(err){ toast(err.message); }
  };
}

async function pageRegister(){
  renderNav();
  regForm.onsubmit = async (e)=>{
    e.preventDefault();
    try{
      const res = await api(ROUTES.register, "POST", {
        fullName: regFullName.value.trim(),
        email: regEmail.value.trim(),
        password: regPassword.value.trim()
      }, false);

      const data = unwrap(res);
      setToken(data.token);
      localStorage.setItem("roles", JSON.stringify(data.roles || []));
      localStorage.setItem("email", data.email || "");
      localStorage.setItem("userId", String(data.userId || ""));
      location.href="index.html";
    }catch(err){ toast(err.message); }
  };
}

async function pageIndex(){
  renderNav();

  async function loadAds(page=0){
    const q = new URLSearchParams();
    q.set("page", page);
    q.set("size", 12);

    if(fBrandId.value) q.set("brandId", fBrandId.value);
    if(fModelId.value) q.set("modelId", fModelId.value);
    if(fPriceMin.value) q.set("priceMin", fPriceMin.value);
    if(fPriceMax.value) q.set("priceMax", fPriceMax.value);
    if(fYearMin.value) q.set("yearMin", fYearMin.value);
    if(fYearMax.value) q.set("yearMax", fYearMax.value);
    if(fCity.value.trim()) q.set("city", fCity.value.trim());
    if(fFuel.value.trim()) q.set("fuelType", fFuel.value.trim());

    const res = await api(ROUTES.adsActive + "?" + q.toString(), "GET");
    const p = unwrap(res);

    const list = p.content || [];
    adsGrid.innerHTML = list.map(ad => `
      <div class="card">
        <img class="ad-img" src="${imgUrl(ad.imageUrl)}" alt="car"/>
        <div class="row" style="margin-top:10px">
          <span class="badge">📍 ${ad.city || "-"}</span>
          <span class="badge">⛽ ${ad.fuelType || "-"}</span>
          <span class="badge">⚙ ${ad.gearbox || "-"}</span>
        </div>
        <h3 style="margin-top:10px">${ad.brand?.name || ""} ${ad.model?.name || ""} • ${ad.releaseYear || ""}</h3>
        <div class="muted">${ad.mileageKm ?? "-"} km • ${ad.engineVolume ?? "-"} L</div>
        <hr/>
        <div class="row" style="justify-content:space-between;align-items:center">
          <b class="price">${ad.price ?? "-"} AZN</b>
          <div class="row">
            <button class="btn accent" onclick="showDetail(${ad.id})">Detail</button>
            ${(getToken() && isRole("ROLE_BUYER")) ? `<button class="btn ok" onclick="addFav(${ad.id})">❤</button>` : ``}
          </div>
        </div>
      </div>
    `).join("");

    pager.innerHTML = `
      <div class="row" style="justify-content:space-between;align-items:center">
        <button class="btn" ${p.first ? "disabled":""} id="prevBtn">Prev</button>
        <span class="muted">Səhifə ${(p.number ?? 0)+1} / ${(p.totalPages ?? 1)}</span>
        <button class="btn" ${p.last ? "disabled":""} id="nextBtn">Next</button>
      </div>
    `;

    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");
    if(prevBtn) prevBtn.onclick = ()=> loadAds((p.number ?? 0)-1);
    if(nextBtn) nextBtn.onclick = ()=> loadAds((p.number ?? 0)+1);
  }

  window.showDetail = async (id)=>{
    try{
      const res = await api(ROUTES.adById(id), "GET");
      const ad = unwrap(res);

      openModal(`
        <div class="modal-head">
          <div>
            <h2>${ad.brand?.name || ""} ${ad.model?.name || ""} • ${ad.releaseYear || ""}</h2>
            <div class="muted">${ad.city || "-"} • ${ad.fuelType || "-"} • ${ad.gearbox || "-"}</div>
          </div>
          <button class="btn" id="modalCloseBtn">Bağla</button>
        </div>
        <div class="modal-body">
          <div class="row">
            <div style="flex:2" class="card">
              <img class="ad-img" style="height:240px" src="${imgUrl(ad.imageUrl)}"/>
              <hr/>
              <div class="muted">Açıqlama</div>
              <div>${(ad.description || "Açıqlama yoxdur").replaceAll("<","&lt;")}</div>
            </div>
            <div style="flex:1" class="card">
              <div class="muted">Qiymət</div><div class="price"><b>${ad.price ?? "-"} AZN</b></div><hr/>
              <div class="muted">Mühərrik</div><b>${ad.engineVolume ?? "-"} L</b><hr/>
              <div class="muted">Yürüş</div><b>${ad.mileageKm ?? "-"} km</b>
            </div>
          </div>
        </div>
      `);
    }catch(e){ toast(e.message); }
  };

  window.addFav = async (adId)=>{
    try{
      await api(ROUTES.favAdd(adId), "POST", null, true);
      toast("Seçilmişə əlavə olundu");
    }catch(e){ toast(e.message); }
  };

  filterBtn.onclick = ()=> loadAds(0);
  loadAds(0);
}

async function pageFavorites(){
  renderNav();

  if(!getToken() || !isRole("ROLE_BUYER")){
    location.href="login.html";
    return;
  }

  async function load(){
    try{
      const res = await api(ROUTES.favorites, "GET", null, true);
      const list = unwrap(res) || [];

      favGrid.innerHTML = list.map(f=>{
        const ad = f.advertisement || f.ad || f;
        return `
          <div class="card">
            <img class="ad-img" src="${imgUrl(ad.imageUrl)}"/>
            <h3 style="margin-top:10px">${ad.brand?.name || ""} ${ad.model?.name || ""} • ${ad.releaseYear || ""}</h3>
            <div class="muted">${ad.city || "-"} • ${ad.fuelType || "-"} • ${ad.gearbox || "-"}</div>
            <hr/>
            <div class="row" style="justify-content:space-between;align-items:center">
              <b class="price">${ad.price ?? "-"} AZN</b>
              <button class="btn danger" onclick="removeFav(${ad.id})">Sil</button>
            </div>
          </div>
        `;
      }).join("");
    }catch(e){ toast(e.message); }
  }

  window.removeFav = async (adId)=>{
    try{
      await api(ROUTES.favDelete(adId), "DELETE", null, true);
      load();
    }catch(e){ toast(e.message); }
  };

  load();
}

async function pageMyAds(){
  renderNav();

  if(!getToken() || !isRole("ROLE_SELLER")){
    location.href="login.html";
    return;
  }

  createAdBtn.onclick = ()=>{
    localStorage.removeItem("editAdId");
    location.href="create-ad.html";
  };

  async function load(page=0){
    const res = await api(ROUTES.adsMine + `?page=${page}&size=12`, "GET", null, true);
    const p = unwrap(res);
    const list = p.content || [];

    myAdsGrid.innerHTML = list.map(ad=>`
      <div class="card">
        <img class="ad-img" src="${imgUrl(ad.imageUrl)}"/>
        <div class="row" style="margin-top:10px">
          <span class="badge">${ad.active ? "✅ ACTIVE" : "⛔ DEACTIVE"}</span>
          <span class="badge">📍 ${ad.city || "-"}</span>
        </div>
        <h3 style="margin-top:10px">${ad.brand?.name || ""} ${ad.model?.name || ""} • ${ad.releaseYear || ""}</h3>
        <div class="muted">${ad.fuelType || "-"} • ${ad.gearbox || "-"} • ${ad.mileageKm ?? "-"} km</div>
        <hr/>
        <div class="row" style="justify-content:space-between;align-items:center">
          <b class="price">${ad.price ?? "-"} AZN</b>
          <div class="row">
            <button class="btn" onclick="editAd(${ad.id})">Edit</button>
            <button class="btn danger" onclick="delAd(${ad.id})">Delete</button>
          </div>
        </div>
      </div>
    `).join("");

    pager.innerHTML = `
      <div class="row" style="justify-content:space-between;align-items:center">
        <button class="btn" ${p.first ? "disabled":""} id="prevBtn">Prev</button>
        <span class="muted">Səhifə ${(p.number ?? 0)+1} / ${(p.totalPages ?? 1)}</span>
        <button class="btn" ${p.last ? "disabled":""} id="nextBtn">Next</button>
      </div>
    `;
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");
    if(prevBtn) prevBtn.onclick = ()=> load((p.number ?? 0)-1);
    if(nextBtn) nextBtn.onclick = ()=> load((p.number ?? 0)+1);
  }

  window.editAd = (id)=>{
    localStorage.setItem("editAdId", String(id));
    location.href="create-ad.html";
  };

  window.delAd = async (id)=>{
    try{
      await api(ROUTES.adDelete(id), "DELETE", null, true);
      load(0);
    }catch(e){ toast(e.message); }
  };

  load(0);
}

async function pageCreateAd(){
  renderNav();

  if(!getToken() || !isRole("ROLE_SELLER")){
    location.href="login.html";
    return;
  }

  const editId = localStorage.getItem("editAdId");
  pageTitle.innerText = editId ? "Elanı redaktə et" : "Yeni elan";

  if(editId){
    try{
      const res = await api(ROUTES.adById(editId), "GET");
      const ad = unwrap(res);

      brandId.value = ad.brand?.id ?? "";
      modelId.value = ad.model?.id ?? "";
      releaseYear.value = ad.releaseYear ?? "";
      engineVolume.value = ad.engineVolume ?? "";
      mileageKm.value = ad.mileageKm ?? "";
      fuelType.value = ad.fuelType ?? "";
      gearbox.value = ad.gearbox ?? "";
      price.value = ad.price ?? "";
      city.value = ad.city ?? "";
      description.value = ad.description ?? "";
      imageUrl.value = ad.imageUrl ?? "";
    }catch(e){ toast(e.message); }
  }

  adForm.onsubmit = async (e)=>{
    e.preventDefault();

    const body = {
      brandId: Number(brandId.value),
      modelId: Number(modelId.value),
      releaseYear: Number(releaseYear.value),
      engineVolume: Number(engineVolume.value),
      mileageKm: Number(mileageKm.value),
      fuelType: fuelType.value.trim(),
      gearbox: gearbox.value.trim(),
      price: Number(price.value),
      city: city.value.trim(),
      description: description.value.trim(),
      imageUrl: imageUrl.value.trim()
    };

    try{
      if(editId){
        await api(ROUTES.adUpdate(editId), "PUT", body, true);
        localStorage.removeItem("editAdId");
      }else{
        await api(ROUTES.adCreate, "POST", body, true);
      }
      location.href="my-ads.html";
    }catch(err){ toast(err.message); }
  };

  cancelBtn.onclick = ()=>{
    localStorage.removeItem("editAdId");
    location.href="my-ads.html";
  };
}

async function pageAdmin(){
  renderNav();

  if(!getToken() || !isRole("ROLE_ADMIN")){
    location.href="login.html";
    return;
  }

  brandForm.onsubmit = async (e)=>{
    e.preventDefault();
    try{
      await api(ROUTES.brands, "POST", { name: brandName.value.trim() }, true);
      brandName.value="";
      await loadBrands();
    }catch(err){ toast(err.message); }
  };

  async function loadBrands(){
    const res = await api(ROUTES.brands, "GET", null, true);
    const list = unwrap(res) || [];
    brandsList.innerHTML = list.map(b=>`
      <div class="row" style="justify-content:space-between;align-items:center">
        <span><b>${b.id}</b> - ${b.name}</span>
        <button class="btn danger" onclick="delBrand(${b.id})">Sil</button>
      </div>
    `).join("<hr/>");
  }

  window.delBrand = async (id)=>{
    try{
      await api(ROUTES.brandDelete(id), "DELETE", null, true);
      await loadBrands();
    }catch(e){ toast(e.message); }
  };

  modelForm.onsubmit = async (e)=>{
    e.preventDefault();
    try{
      await api(ROUTES.models, "POST", {
        name: modelName.value.trim(),
        brandId: Number(modelBrandId.value)
      }, true);
      modelName.value=""; modelBrandId.value="";
      await loadModels();
    }catch(err){ toast(err.message); }
  };

  async function loadModels(){
    const res = await api(ROUTES.models, "GET", null, true);
    const list = unwrap(res) || [];
    modelsList.innerHTML = list.map(m=>`
      <div class="row" style="justify-content:space-between;align-items:center">
        <span><b>${m.id}</b> - ${m.name} <span class="muted">(brand: ${m.brand?.name || "-"})</span></span>
        <button class="btn danger" onclick="delModel(${m.id})">Sil</button>
      </div>
    `).join("<hr/>");
  }

  window.delModel = async (id)=>{
    try{
      await api(ROUTES.modelDelete(id), "DELETE", null, true);
      await loadModels();
    }catch(e){ toast(e.message); }
  };

  async function loadAds(page=0){
    const res = await api(ROUTES.adsAll + `?page=${page}&size=12`, "GET", null, true);
    const p = unwrap(res);
    const list = p.content || [];

    adminAds.innerHTML = list.map(ad=>`
      <div class="card">
        <div class="row" style="justify-content:space-between;align-items:center">
          <span class="badge">${ad.active ? "✅ ACTIVE" : "⛔ DEACTIVE"}</span>
          <span class="badge">👤 ${ad.owner?.email || "-"}</span>
        </div>
        <h3 style="margin-top:10px">${ad.brand?.name || ""} ${ad.model?.name || ""} • ${ad.releaseYear || ""}</h3>
        <div class="muted">${ad.city || "-"} • ${ad.fuelType || "-"} • ${ad.gearbox || "-"}</div>
        <hr/>
        <div class="row" style="justify-content:space-between;align-items:center">
          <b class="price">${ad.price ?? "-"} AZN</b>
          <div class="row">
            <button class="btn warn" onclick="toggleAd(${ad.id}, ${!ad.active})">${ad.active?"Deactivate":"Activate"}</button>
            <button class="btn danger" onclick="adminDelAd(${ad.id})">Delete</button>
          </div>
        </div>
      </div>
    `).join("");

    pager.innerHTML = `
      <div class="row" style="justify-content:space-between;align-items:center">
        <button class="btn" ${p.first ? "disabled":""} id="prevBtn">Prev</button>
        <span class="muted">Səhifə ${(p.number ?? 0)+1} / ${(p.totalPages ?? 1)}</span>
        <button class="btn" ${p.last ? "disabled":""} id="nextBtn">Next</button>
      </div>
    `;
    const prevBtn = document.getElementById("prevBtn");
    const nextBtn = document.getElementById("nextBtn");
    if(prevBtn) prevBtn.onclick = ()=> loadAds((p.number ?? 0)-1);
    if(nextBtn) nextBtn.onclick = ()=> loadAds((p.number ?? 0)+1);
  }

  window.toggleAd = async (id, active)=>{
    try{
      await api(ROUTES.adStatus(id) + `?active=${active}`, "PUT", null, true);
      await loadAds(0);
    }catch(e){ toast(e.message); }
  };

  window.adminDelAd = async (id)=>{
    try{
      await api(ROUTES.adDelete(id), "DELETE", null, true);
      await loadAds(0);
    }catch(e){ toast(e.message); }
  };

  try{
    await loadBrands();
    await loadModels();
    await loadAds(0);
  }catch(e){ toast(e.message); }
}

window.addEventListener("DOMContentLoaded", ()=>{
  const page = document.body.getAttribute("data-page");
  if(page==="login") pageLogin();
  if(page==="register") pageRegister();
  if(page==="index") pageIndex();
  if(page==="favorites") pageFavorites();
  if(page==="myads") pageMyAds();
  if(page==="createad") pageCreateAd();
  if(page==="admin") pageAdmin();
});

const API = "http://localhost:8080";

const ROUTES = {
  register: "/api/auth/register",
  login: "/api/auth/login",
  becomeSeller: "/api/users/become-seller",

  adsActive: "/api/ads/active",
  adsMine: "/api/ads/mine",
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
  favoriteAdd: (adId) => `/api/favorites/${adId}`,
  favoriteRemove: (adId) => `/api/favorites/${adId}`,
};

const STORAGE = {
  token: "token",
  roles: "roles",
  email: "email",
  userId: "userId",
};

const $ = (id) => document.getElementById(id);

function pageName() {
  return document.body?.dataset?.page || "";
}

function getToken() {
  return localStorage.getItem(STORAGE.token) || "";
}

function getRoles() {
  try {
    const raw = localStorage.getItem(STORAGE.roles);
    const roles = raw ? JSON.parse(raw) : [];
    return Array.isArray(roles) ? roles : [];
  } catch {
    return [];
  }
}

function hasRole(role) {
  return getRoles().includes(role);
}

function isLoggedIn() {
  return !!getToken();
}

function logout() {
  localStorage.removeItem(STORAGE.token);
  localStorage.removeItem(STORAGE.roles);
  localStorage.removeItem(STORAGE.email);
  localStorage.removeItem(STORAGE.userId);
  window.location.href = "index.html";
}

function toast(msg, ok = false) {
  const t = $("toast");
  if (!t) return alert(msg);
  t.textContent = msg;
  t.classList.toggle("ok", !!ok);
  t.classList.add("show");
  clearTimeout(toast._timer);
  toast._timer = setTimeout(() => t.classList.remove("show"), 2600);
}

function qs(paramsObj) {
  const u = new URLSearchParams();
  Object.entries(paramsObj || {}).forEach(([k, v]) => {
    if (v === null || v === undefined || v === "") return;
    u.set(k, String(v));
  });
  const s = u.toString();
  return s ? `?${s}` : "";
}

async function apiFetch(path, { method = "GET", body = null, auth = false } = {}) {
  const headers = { "Content-Type": "application/json" };
  if (auth) {
    const token = getToken();
    if (!token) throw new Error("Login tələb olunur.");
    headers.Authorization = `Bearer ${token}`;
  }

  const res = await fetch(API + path, {
    method,
    headers,
    body: body ? JSON.stringify(body) : null,
  });

  let json = null;
  const text = await res.text();
  if (text) {
    try {
      json = JSON.parse(text);
    } catch {
      json = text;
    }
  }

  if (res.status === 401) {
    if (auth) {
      toast("Sessiya bitdi. Yenidən login ol.", false);
      logout();
      throw new Error("Unauthorized");
    }
  }

  if (!res.ok) {
    const msg =
      (json && json.message) ||
      (typeof json === "string" ? json : null) ||
      `Xəta: ${res.status}`;
    throw new Error(msg);
  }

  if (json && typeof json === "object" && "success" in json && "data" in json) {
    if (!json.success) throw new Error(json.message || "Xəta");
    return json.data;
  }

  
  return json;
}


function renderNav() {
  const nav = $("navLinks");
  if (!nav) return;

  const roles = getRoles();
  const logged = isLoggedIn();

  const links = [];
  links.push(`<a class="link" href="index.html">Elanlar</a>`);

  if (!logged) {
    links.push(`<a class="link" href="login.html">Login</a>`);
    links.push(`<a class="link" href="register.html">Register</a>`);
  } else {
    if (roles.includes("ROLE_BUYER")) {
      links.push(`<a class="link" href="favorites.html">Seçilmişlər</a>`);
    }
    if (roles.includes("ROLE_SELLER")) {
      links.push(`<a class="link" href="my-ads.html">Mənim elanlar</a>`);
      links.push(`<a class="link" href="create-ad.html">Elan yarat</a>`);
    }
    if (roles.includes("ROLE_ADMIN")) {
      links.push(`<a class="link" href="admin.html">Admin</a>`);
    }

    if (roles.includes("ROLE_BUYER") && !roles.includes("ROLE_SELLER")) {
      links.push(`<button class="btn warn" id="becomeSellerBtn" type="button">Seller ol</button>`);
    }

    links.push(`<button class="btn" id="logoutBtn" type="button">Çıxış</button>`);
  }

  nav.innerHTML = links.join("");

  const logoutBtn = $("logoutBtn");
  if (logoutBtn) logoutBtn.onclick = logout;

  const becomeBtn = $("becomeSellerBtn");
  if (becomeBtn) {
    becomeBtn.onclick = async () => {
      try {
        await apiFetch(ROUTES.becomeSeller, { method: "PUT", auth: true });
        toast("Seller olundu. Yenidən login ol!", true);
        logout();
      } catch (e) {
        toast(e.message || "Xəta", false);
      }
    };
  }
}


function openModal(html) {
  const back = $("modalBack");
  const body = $("modalBody");
  if (!back || !body) return;
  body.innerHTML = html;
  back.classList.add("show");
}

function closeModal() {
  const back = $("modalBack");
  const body = $("modalBody");
  if (!back || !body) return;
  back.classList.remove("show");
  body.innerHTML = "";
}

function initModalClose() {
  const back = $("modalBack");
  if (!back) return;
  back.addEventListener("click", (e) => {
    if (e.target === back) closeModal();
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") closeModal();
  });
}

/* ---------------- ADS UI HELPERS ---------------- */

function safe(v) {
  return v === null || v === undefined ? "" : String(v);
}

function adCard(ad, { showFavBtn = true, showEditBtns = false, showAdminBtns = false } = {}) {
  const brandName = ad?.brand?.name || "";
  const modelName = ad?.model?.name || "";
  const title = `${brandName} ${modelName}`.trim() || "Elan";

  const year = ad?.releaseYear ?? "";
  const city = ad?.city ?? "";
  const price = ad?.price ?? "";
  const fuel = ad?.fuelType ?? "";
  const gearbox = ad?.gearbox ?? "";
  const km = ad?.mileageKm ?? "";
  const img = ad?.imageUrl || "";

  const id = ad?.id;

  const favBtn =
    showFavBtn && hasRole("ROLE_BUYER")
      ? `<button class="btn" data-act="fav" data-id="${id}">❤️ Seçilmiş</button>`
      : "";

  const editBtns =
    showEditBtns
      ? `
      <button class="btn ok" data-act="edit" data-id="${id}">✏️ Edit</button>
      <button class="btn danger" data-act="del" data-id="${id}">🗑️ Sil</button>
    `
      : "";

  const adminBtns =
    showAdminBtns
      ? `
      <button class="btn warn" data-act="toggle" data-id="${id}">🔁 Status</button>
      <button class="btn danger" data-act="del" data-id="${id}">🗑️ Sil</button>
    `
      : "";

  return `
  <div class="card ad-card">
    <div class="ad-img">${img ? `<img src="${img}" alt="img" />` : `<div class="noimg">No image</div>`}</div>
    <div class="ad-body">
      <div class="ad-title">${safe(title)}</div>
      <div class="ad-meta">
        <span class="badge">💰 ${safe(price)}</span>
        <span class="badge">📅 ${safe(year)}</span>
        <span class="badge">📍 ${safe(city)}</span>
      </div>
      <div class="muted" style="margin-top:8px">
        Yanacaq: <b>${safe(fuel)}</b> • Qutu: <b>${safe(gearbox)}</b> • Km: <b>${safe(km)}</b>
      </div>

      <div class="actions" style="margin-top:10px; display:flex; gap:8px; flex-wrap:wrap">
        <button class="btn" data-act="detail" data-id="${id}">🔎 Detail</button>
        ${favBtn}
        ${editBtns}
        ${adminBtns}
      </div>
    </div>
  </div>
  `;
}

async function showAdDetail(id) {
  try {
    const ad = await apiFetch(ROUTES.adById(id));
    const brandName = ad?.brand?.name || "";
    const modelName = ad?.model?.name || "";
    const title = `${brandName} ${modelName}`.trim() || "Elan";

    openModal(`
      <div class="modal-card">
        <div style="display:flex; justify-content:space-between; gap:12px; align-items:flex-start">
          <div>
            <h3 style="margin:0">${safe(title)}</h3>
            <div class="muted" style="margin-top:6px">
              ${safe(ad?.city)} • ${safe(ad?.releaseYear)} • ${safe(ad?.fuelType)} • ${safe(ad?.gearbox)}
            </div>
          </div>
          <button class="btn" onclick="(${closeModal.toString()})()">✖</button>
        </div>
        <hr/>
        <div class="grid" style="grid-template-columns:1fr 1fr; gap:10px">
          <div><b>Qiymət:</b> ${safe(ad?.price)}</div>
          <div><b>Mileage:</b> ${safe(ad?.mileageKm)}</div>
          <div><b>Engine:</b> ${safe(ad?.engineVolume)}</div>
          <div><b>BrandId:</b> ${safe(ad?.brand?.id)}</div>
          <div><b>ModelId:</b> ${safe(ad?.model?.id)}</div>
        </div>
        <div style="margin-top:10px">
          <b>Açıqlama:</b>
          <div class="muted" style="margin-top:6px; white-space:pre-wrap">${safe(ad?.description)}</div>
        </div>
      </div>
    `);
  } catch (e) {
    toast(e.message || "Xəta", false);
  }
}


function renderPager(containerId, page, totalPages, onGo) {
  const el = $(containerId);
  if (!el) return;

  const p = Number(page || 0);
  const t = Number(totalPages || 0);

  if (t <= 1) {
    el.innerHTML = "";
    return;
  }

  const prevDisabled = p <= 0 ? "disabled" : "";
  const nextDisabled = p >= t - 1 ? "disabled" : "";

  const buttons = [];
  const maxBtns = 7;
  let start = Math.max(0, p - 3);
  let end = Math.min(t - 1, start + (maxBtns - 1));
  start = Math.max(0, end - (maxBtns - 1));

  for (let i = start; i <= end; i++) {
    const active = i === p ? "active" : "";
    buttons.push(`<button class="btn ${active}" data-page="${i}">${i + 1}</button>`);
  }

  el.innerHTML = `
    <div style="display:flex; gap:8px; align-items:center; flex-wrap:wrap">
      <button class="btn" data-prev="1" ${prevDisabled}>⬅</button>
      ${buttons.join("")}
      <button class="btn" data-next="1" ${nextDisabled}>➡</button>
      <span class="muted">Səhifə ${p + 1}/${t}</span>
    </div>
  `;

  el.onclick = (e) => {
    const btn = e.target.closest("button");
    if (!btn) return;
    if (btn.dataset.prev && p > 0) return onGo(p - 1);
    if (btn.dataset.next && p < t - 1) return onGo(p + 1);
    if (btn.dataset.page) return onGo(Number(btn.dataset.page));
  };
}


async function loadActiveAds(page = 0) {
  const grid = $("adsGrid");
  if (!grid) return;

  const params = {
    page,
    size: 12,
    brandId: $("fBrandId")?.value,
    modelId: $("fModelId")?.value,
    priceMin: $("fPriceMin")?.value,
    priceMax: $("fPriceMax")?.value,
    yearMin: $("fYearMin")?.value,
    yearMax: $("fYearMax")?.value,
    city: $("fCity")?.value,
    fuelType: $("fFuel")?.value,
  };

  try {
    const pageObj = await apiFetch(ROUTES.adsActive + qs(params));
    const content = pageObj?.content || [];
    const totalPages = pageObj?.totalPages ?? 1;

    grid.innerHTML = content.map((ad) => adCard(ad, { showFavBtn: true })).join("") || `<div class="muted">Elan yoxdur</div>`;

    grid.onclick = async (e) => {
      const btn = e.target.closest("button");
      if (!btn) return;
      const id = btn.dataset.id;
      const act = btn.dataset.act;

      if (act === "detail") return showAdDetail(id);

      if (act === "fav") {
        if (!isLoggedIn() || !hasRole("ROLE_BUYER")) {
          toast("Favorites üçün BUYER login olmalıdır.", false);
          return;
        }
        try {
          await apiFetch(ROUTES.favoriteAdd(id), { method: "POST", auth: true });
          toast("Seçilmişə əlavə olundu", true);
        } catch (err) {
          toast(err.message || "Xəta", false);
        }
      }
    };

    renderPager("pager", page, totalPages, (p) => loadActiveAds(p));
  } catch (e) {
    toast(e.message || "Xəta", false);
    grid.innerHTML = `<div class="muted">Yüklənmədi</div>`;
  }
}

function initIndex() {
  const filterBtn = $("filterBtn");
  if (filterBtn) {
    filterBtn.onclick = () => loadActiveAds(0);
  }
  initModalClose();
  loadActiveAds(0);
}


function initLogin() {
  const form = $("loginForm");
  if (!form) return;

  form.onsubmit = async (e) => {
    e.preventDefault();
    const email = $("loginEmail")?.value?.trim();
    const password = $("loginPassword")?.value;

    if (!email || !password) return toast("Email və password boş ola bilməz", false);

    try {
      const authData = await apiFetch(ROUTES.login, {
        method: "POST",
        body: { email, password },
        auth: false,
      });

      localStorage.setItem(STORAGE.token, authData.token || "");
      localStorage.setItem(STORAGE.userId, String(authData.userId ?? ""));
      localStorage.setItem(STORAGE.email, authData.email || email);
      localStorage.setItem(STORAGE.roles, JSON.stringify(authData.roles || []));

      toast("Uğurla login oldun", true);
      window.location.href = "index.html";
    } catch (err) {
      toast(err.message || "Login xətası", false);
    }
  };
}

function initRegister() {
  const form = $("regForm");
  if (!form) return;

  form.onsubmit = async (e) => {
    e.preventDefault();
    const fullName = $("regFullName")?.value?.trim();
    const email = $("regEmail")?.value?.trim();
    const password = $("regPassword")?.value;

    if (!fullName || !email || !password) return toast("Bütün sahələri doldur", false);

    try {
      await apiFetch(ROUTES.register, {
        method: "POST",
        body: { fullName, email, password },
        auth: false,
      });
      toast("Qeydiyyat uğurlu. Login et.", true);
      window.location.href = "login.html";
    } catch (err) {
      toast(err.message || "Register xətası", false);
    }
  };
}


function requireRoleOrRedirect(role, to = "index.html") {
  if (!isLoggedIn() || !hasRole(role)) {
    toast("Bu səhifə üçün icazə yoxdur", false);
    window.location.href = to;
    return false;
  }
  return true;
}

async function initCreateAd() {
  if (!requireRoleOrRedirect("ROLE_SELLER")) return;

  const form = $("adForm");
  if (!form) return;

  const url = new URL(window.location.href);
  const editId = url.searchParams.get("id");

  if (editId) {
    // edit mode
    $("pageTitle") && ($("pageTitle").textContent = "Elanı yenilə");
    try {
      const ad = await apiFetch(ROUTES.adById(editId), { auth: false });
      // form doldur: backend nested brand/model qaytarır
      $("brandId").value = ad?.brand?.id ?? "";
      $("modelId").value = ad?.model?.id ?? "";
      $("releaseYear").value = ad?.releaseYear ?? "";
      $("engineVolume").value = ad?.engineVolume ?? "";
      $("mileageKm").value = ad?.mileageKm ?? "";
      $("fuelType").value = ad?.fuelType ?? "";
      $("gearbox").value = ad?.gearbox ?? "";
      $("price").value = ad?.price ?? "";
      $("city").value = ad?.city ?? "";
      $("description").value = ad?.description ?? "";
      $("imageUrl").value = ad?.imageUrl ?? "";
    } catch (e) {
      toast(e.message || "Elan yüklənmədi", false);
    }
  }

  const cancelBtn = $("cancelBtn");
  if (cancelBtn) cancelBtn.onclick = () => window.location.href = "my-ads.html";

  form.onsubmit = async (e) => {
    e.preventDefault();

    const payload = {
      brandId: Number($("brandId")?.value),
      modelId: Number($("modelId")?.value),
      releaseYear: Number($("releaseYear")?.value),
      engineVolume: Number($("engineVolume")?.value),
      mileageKm: Number($("mileageKm")?.value),
      fuelType: $("fuelType")?.value?.trim(),
      gearbox: $("gearbox")?.value?.trim(),
      price: $("price")?.value ? Number($("price").value) : null,
      city: $("city")?.value?.trim(),
      description: $("description")?.value?.trim(),
      imageUrl: $("imageUrl")?.value?.trim(),
    };

    if (!payload.brandId || !payload.modelId || !payload.price || !payload.releaseYear) {
      toast("BrandId, ModelId, İl və Qiymət mütləqdir", false);
      return;
    }

    try {
      if (!editId) {
        await apiFetch(ROUTES.adCreate, { method: "POST", body: payload, auth: true });
        toast("Elan yaradıldı", true);
      } else {
        await apiFetch(ROUTES.adUpdate(editId), { method: "PUT", body: payload, auth: true });
        toast("Elan yeniləndi", true);
      }
      window.location.href = "my-ads.html";
    } catch (err) {
      toast(err.message || "Xəta", false);
    }
  };
}

async function loadMyAds(page = 0) {
  if (!requireRoleOrRedirect("ROLE_SELLER")) return;

  const grid = $("myAdsGrid");
  if (!grid) return;

  try {
    const pageObj = await apiFetch(ROUTES.adsMine + qs({ page, size: 12 }), { auth: true });
    const content = pageObj?.content || [];
    const totalPages = pageObj?.totalPages ?? 1;

    grid.innerHTML = content.map((ad) => adCard(ad, { showFavBtn: false, showEditBtns: true })).join("") || `<div class="muted">Elan yoxdur</div>`;

    grid.onclick = async (e) => {
      const btn = e.target.closest("button");
      if (!btn) return;
      const id = btn.dataset.id;
      const act = btn.dataset.act;

      if (act === "detail") return showAdDetail(id);

      if (act === "edit") {
        window.location.href = `create-ad.html?id=${encodeURIComponent(id)}`;
        return;
      }

      if (act === "del") {
        if (!confirm("Silmək istədiyinə əminsən?")) return;
        try {
          await apiFetch(ROUTES.adDelete(id), { method: "DELETE", auth: true });
          toast("Silindi", true);
          loadMyAds(page);
        } catch (err) {
          toast(err.message || "Xəta", false);
        }
      }
    };

    const createBtn = $("createAdBtn");
    if (createBtn) createBtn.onclick = () => (window.location.href = "create-ad.html");

    renderPager("pager", page, totalPages, (p) => loadMyAds(p));
  } catch (e) {
    toast(e.message || "Xəta", false);
    grid.innerHTML = `<div class="muted">Yüklənmədi</div>`;
  }
}


async function loadFavorites() {
  if (!requireRoleOrRedirect("ROLE_BUYER")) return;

  const grid = $("favGrid");
  if (!grid) return;

  try {
    const list = await apiFetch(ROUTES.favorites, { auth: true });
    const ads = (list || []).map((f) => f?.ad).filter(Boolean);

    grid.innerHTML =
      ads.map((ad) => adCard(ad, { showFavBtn: false })).join("") || `<div class="muted">Seçilmiş yoxdur</div>`;

    grid.onclick = async (e) => {
      const btn = e.target.closest("button");
      if (!btn) return;
      const id = btn.dataset.id;
      const act = btn.dataset.act;

      if (act === "detail") return showAdDetail(id);

    };
  } catch (e) {
    toast(e.message || "Xəta", false);
    grid.innerHTML = `<div class="muted">Yüklənmədi</div>`;
  }
}


async function loadBrandsAndModels() {
  const brandsList = $("brandsList");
  const modelsList = $("modelsList");

  try {
    const brands = await apiFetch(ROUTES.brands, { auth: true });
    if (brandsList) {
      brandsList.innerHTML =
        (brands || [])
          .map(
            (b) => `
          <div class="row">
            <div><b>${safe(b.name)}</b> <span class="muted">#${safe(b.id)}</span></div>
            <div style="display:flex; gap:8px">
              <button class="btn danger" data-act="delBrand" data-id="${b.id}">Sil</button>
            </div>
          </div>
        `
          )
          .join("") || `<div class="muted">Brand yoxdur</div>`;
    }
  } catch (e) {
    toast(e.message || "Brand yüklənmədi", false);
  }

  try {
    const models = await apiFetch(ROUTES.models, { auth: true });
    if (modelsList) {
      modelsList.innerHTML =
        (models || [])
          .map((m) => {
            const bName = m?.brand?.name || "";
            const bId = m?.brand?.id || "";
            return `
            <div class="row">
              <div><b>${safe(m.name)}</b> <span class="muted">#${safe(m.id)}</span> • <span class="badge">${safe(bName)} (#${safe(bId)})</span></div>
              <div style="display:flex; gap:8px">
                <button class="btn danger" data-act="delModel" data-id="${m.id}">Sil</button>
              </div>
            </div>
          `;
          })
          .join("") || `<div class="muted">Model yoxdur</div>`;
    }
  } catch (e) {
    toast(e.message || "Model yüklənmədi", false);
  }

  if (brandsList) {
    brandsList.onclick = async (e) => {
      const btn = e.target.closest("button");
      if (!btn) return;
      if (btn.dataset.act !== "delBrand") return;
      if (!confirm("Brand silinsin?")) return;
      try {
        await apiFetch(ROUTES.brandDelete(btn.dataset.id), { method: "DELETE", auth: true });
        toast("Brand silindi", true);
        loadBrandsAndModels();
      } catch (err) {
        toast(err.message || "Xəta", false);
      }
    };
  }

  if (modelsList) {
    modelsList.onclick = async (e) => {
      const btn = e.target.closest("button");
      if (!btn) return;
      if (btn.dataset.act !== "delModel") return;
      if (!confirm("Model silinsin?")) return;
      try {
        await apiFetch(ROUTES.modelDelete(btn.dataset.id), { method: "DELETE", auth: true });
        toast("Model silindi", true);
        loadBrandsAndModels();
      } catch (err) {
        toast(err.message || "Xəta", false);
      }
    };
  }
}

async function loadAdminAds(page = 0) {
  const grid = $("adminAds");
  if (!grid) return;

  try {
    const pageObj = await apiFetch(ROUTES.adsActive + qs({ page, size: 12 }), { auth: true });
    const content = pageObj?.content || [];
    const totalPages = pageObj?.totalPages ?? 1;

    grid.innerHTML =
      content.map((ad) => adCard(ad, { showFavBtn: false, showAdminBtns: true })).join("") || `<div class="muted">Elan yoxdur</div>`;

    grid.onclick = async (e) => {
      const btn = e.target.closest("button");
      if (!btn) return;
      const id = btn.dataset.id;
      const act = btn.dataset.act;

      if (act === "detail") return showAdDetail(id);

      if (act === "toggle") {
        try {
          await apiFetch(ROUTES.adStatus(id), { method: "PUT", auth: true });
          toast("Status dəyişdi", true);
          loadAdminAds(page);
        } catch (err) {
          toast(err.message || "Xəta", false);
        }
      }

      if (act === "del") {
        if (!confirm("Elan silinsin?")) return;
        try {
          await apiFetch(ROUTES.adDelete(id), { method: "DELETE", auth: true });
          toast("Silindi", true);
          loadAdminAds(page);
        } catch (err) {
          toast(err.message || "Xəta", false);
        }
      }
    };

    renderPager("pager", page, totalPages, (p) => loadAdminAds(p));
  } catch (e) {
    toast(e.message || "Xəta", false);
    grid.innerHTML = `<div class="muted">Yüklənmədi</div>`;
  }
}

function initAdmin() {
  if (!requireRoleOrRedirect("ROLE_ADMIN")) return;

  const brandForm = $("brandForm");
  if (brandForm) {
    brandForm.onsubmit = async (e) => {
      e.preventDefault();
      const name = $("brandName")?.value?.trim();
      if (!name) return toast("Brand adı boş ola bilməz", false);
      try {
        await apiFetch(ROUTES.brands, { method: "POST", body: { name }, auth: true });
        $("brandName").value = "";
        toast("Brand əlavə olundu", true);
        loadBrandsAndModels();
      } catch (err) {
        toast(err.message || "Xəta", false);
      }
    };
  }

  const modelForm = $("modelForm");
  if (modelForm) {
    modelForm.onsubmit = async (e) => {
      e.preventDefault();
      const name = $("modelName")?.value?.trim();
      const brandId = Number($("modelBrandId")?.value);
      if (!name || !brandId) return toast("Model adı və brandId lazımdır", false);

      try {
        await apiFetch(ROUTES.models, { method: "POST", body: { name, brandId }, auth: true });
        $("modelName").value = "";
        $("modelBrandId").value = "";
        toast("Model əlavə olundu", true);
        loadBrandsAndModels();
      } catch (err) {
        toast(err.message || "Xəta", false);
      }
    };
  }

  initModalClose();
  loadBrandsAndModels();
  loadAdminAds(0);
}


function boot() {
  renderNav();

  const p = pageName();
  if (p === "index") return initIndex();
  if (p === "login") return initLogin();
  if (p === "register") return initRegister();
  if (p === "create-ad") return initCreateAd();
  if (p === "my-ads") return loadMyAds(0);
  if (p === "favorites") return loadFavorites();
  if (p === "admin") return initAdmin();
}

document.addEventListener("DOMContentLoaded", boot);
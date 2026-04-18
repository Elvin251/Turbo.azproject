package az.developia.turbo_system_name.Tasks.lesson93practice;

import jakarta.validation.constraints.*;

import java.time.LocalDate;


public class AddProfileRequest {

    @NotBlank(message = "Ad mutleqdir")
    private String name;

    @NotBlank(message = "Soyad mutleqdir")
    private String surname;

    @NotNull(message = "Dogum tarixi mutleqdir")
    @Past(message = "Dogum tarixi kecmisde olmalidir")
    private LocalDate birthday;

    @NotBlank(message = "Github link mutleqdir")
    @Pattern(
            regexp = "^(https?:\\/\\/)?(www\\.)?github\\.com\\/.*$",
            message = "Github link duzgun formatda olmalidir"
    )
    private String githubUrl;

    @NotBlank(message = "Summary mutleqdir")
    @Size(max = 2000, message = "Summary maksimum 2000 simvol ola biler")
    private String summary;

    @NotNull(message = "Salary mutleqdir")
    @Min(value = 100, message = "Minimum salary 100 olmalidir")
    @Max(value = 100000, message = "Maksimum salary 100000 ola biler")
    private Integer salary;

    @NotBlank(message = "Email mutleqdir")
    @Email(message = "Email duzgun formatda deyil")
    private String email;

    @NotBlank(message = "Telefon mutleqdir")
    @Pattern(
            regexp = "^0\\d{2}-\\d{3}-\\d{2}-\\d{2}$",
            message = "Telefon formati 055-909-88-77 seklinde olmalidir"
    )
    private String phone;
}
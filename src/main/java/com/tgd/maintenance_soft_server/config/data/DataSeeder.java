package com.tgd.maintenance_soft_server.config.data;

import com.tgd.maintenance_soft_server.modules.company.entities.CompanyEntity;
import com.tgd.maintenance_soft_server.modules.company.repositories.CompanyRepository;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormFieldEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormOptionEntity;
import com.tgd.maintenance_soft_server.modules.form.models.FormFieldType;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormFieldRepository;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormOptionRepository;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceTypeEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceTypeRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.plant.repositories.PlantRepository;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;
    private final MaintenanceTypeRepository maintenanceTypeRepository;
    private final FormRepository formRepository;
    private final FormFieldRepository formFieldRepository;
    private final FormOptionRepository formOptionRepository;

    private CompanyEntity company;
    private List<UserEntity> users;
    private List<PlantEntity> plants;
    private List<MaintenanceTypeEntity> maintenanceTypes;

    @Override
    public void run(String... args) throws Exception {
        seedCompany();
        seedUsers();
        seedPlants();
        seedMaintenanceTypes();
        seedForms();
    }

    private void seedCompany() {
        if (companyRepository.count() == 0) {
            company = new CompanyEntity();
            company.setName("TechGlobal Solutions");
            companyRepository.save(company);
            System.out.println("🏢 Empresa cargada");
        }
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            UserEntity manager = new UserEntity();
            manager.setAuth0Id("auth0|67f70aa795b0469a2658e7e0");
            manager.setEmail("plant-manager@mail.com");
            manager.setName("Plant Manager");
            manager.setRole("PLANT_MANAGER");
            manager.setImage("https://s.gravatar.com/avatar/dde9f4169bcf675751b4b4da24682956?s=480&r=pg&d=https://cdn.auth0.com/avatars/pl.png");
            manager.setCompany(company);
            manager.setCreatedAt(LocalDateTime.now());
            manager.setUpdatedAt(LocalDateTime.now());

            UserEntity supervisor = new UserEntity();
            supervisor.setAuth0Id("auth0|67fdd4ededfc67117cfdd43b");
            supervisor.setEmail("plant-supervisor@mail.com");
            supervisor.setName("Plant Supervisor");
            supervisor.setRole("PLANT_SUPERVISOR");
            supervisor.setImage("https://s.gravatar.com/avatar/66d2486781c277b4d4d00acb2c599130?s=480&r=pg&d=https://cdn.auth0.com/avatars/pl.png");
            supervisor.setCompany(company);
            supervisor.setCreatedAt(LocalDateTime.now());
            supervisor.setUpdatedAt(LocalDateTime.now());

            UserEntity operator = new UserEntity();
            operator.setAuth0Id("auth0|67fdd5a0c9b41c726c98cd6d");
            operator.setEmail("plant-operator@mail.com");
            operator.setName("Plant Operator");
            operator.setRole("PLANT_OPERATOR");
            operator.setImage("https://s.gravatar.com/avatar/66d2486781c277b4d4d00acb2c599130?s=480&r=pg&d=https://cdn.auth0.com/avatars/pl.png");
            operator.setCompany(company);
            operator.setCreatedAt(LocalDateTime.now());
            operator.setUpdatedAt(LocalDateTime.now());

            users = List.of(manager, supervisor, operator);

            userRepository.saveAll(users);
        }
    }

    private void seedPlants() {
        if (plantRepository.count() == 0) {
            PlantEntity plant1 = new PlantEntity();
            plant1.setName("Planta Buenos Aires");
            plant1.setLocation("Av. Libertador 1280, CABA");
            plant1.setCompany(company);
            plant1.setCreatedAt(LocalDateTime.now());
            plant1.setUpdatedAt(LocalDateTime.now());

            PlantEntity plant2 = new PlantEntity();
            plant2.setName("Planta Córdoba");
            plant2.setLocation("Av. Colón 5678, Córdoba");
            plant2.setCompany(company);
            plant2.setCreatedAt(LocalDateTime.now());
            plant2.setUpdatedAt(LocalDateTime.now());

            PlantEntity plant3 = new PlantEntity();
            plant3.setName("Planta Rosario");
            plant3.setLocation("Bv. Oroño 910, Rosario");
            plant3.setCompany(company);
            plant3.setCreatedAt(LocalDateTime.now());
            plant3.setUpdatedAt(LocalDateTime.now());

            plants = List.of(plant1, plant2, plant3);

            for (PlantEntity plant : plants) {
                plant.assignUser(users.get(0));
            }

            plantRepository.saveAll(plants);
        }
    }

    private void seedMaintenanceTypes() {
        if (maintenanceTypeRepository.count() == 0) {
            MaintenanceTypeEntity maintenanceType1 = new MaintenanceTypeEntity();
            maintenanceType1.setName("Lubrication");
            maintenanceType1.setDescription("Periodic lubrication of equipment");
            maintenanceType1.setIdentifyingEntity(plants.get(0));

            MaintenanceTypeEntity maintenanceType2 = new MaintenanceTypeEntity();
            maintenanceType2.setName("Corrective");
            maintenanceType2.setDescription("Repair after a detected failure");
            maintenanceType2.setIdentifyingEntity(plants.get(0));

            MaintenanceTypeEntity maintenanceType3 = new MaintenanceTypeEntity();
            maintenanceType3.setName("Inspection");
            maintenanceType3.setDescription("Routine visual checks");
            maintenanceType3.setIdentifyingEntity(plants.get(0));

            maintenanceTypes = List.of(maintenanceType1, maintenanceType2, maintenanceType3);

            maintenanceTypeRepository.saveAll(maintenanceTypes);
        }
    }

    private void seedForms() {
        if (formRepository.count() == 0) {
            // ======================================================================================================

            FormEntity form1 = new FormEntity();
            form1.setName("Lubrication Form");
            form1.setDescription("Form used for scheduled lubrication checks");
            form1.setMaintenanceType(maintenanceTypes.get(0));
            form1.setIdentifyingEntity(plants.get(0));

            FormEntity form2 = new FormEntity();
            form2.setName("Corrective Maintenance Form");
            form2.setDescription("Form used to record corrective actions after a failure");
            form2.setMaintenanceType(maintenanceTypes.get(1));
            form2.setIdentifyingEntity(plants.get(0));

            FormEntity form3 = new FormEntity();
            form3.setName("Visual Inspection Checklist");
            form3.setDescription("Checklist for performing visual inspections of equipment");
            form3.setMaintenanceType(maintenanceTypes.get(2));
            form3.setIdentifyingEntity(plants.get(0));

            formRepository.saveAll(List.of(form1, form2, form3));

            // ======================================================================================================

            // Lubrication Form:
            FormFieldEntity field1 = new FormFieldEntity();
            field1.setName("Lubricant Type");
            field1.setType(FormFieldType.TEXT);
            field1.setRequired(true);
            field1.setOrder(1);
            field1.setForm(form1);
            field1.setIdentifyingEntity(plants.get(0));

            FormFieldEntity field2 = new FormFieldEntity();
            field2.setName("Quantity Applied (ml)");
            field2.setType(FormFieldType.NUMBER);
            field2.setRequired(true);
            field2.setOrder(2);
            field2.setForm(form1);
            field2.setIdentifyingEntity(plants.get(0));

            FormFieldEntity field3 = new FormFieldEntity();
            field3.setName("Application Date");
            field3.setType(FormFieldType.DATE);
            field3.setRequired(true);
            field3.setOrder(3);
            field3.setForm(form1);
            field3.setIdentifyingEntity(plants.get(0));

            FormFieldEntity field4 = new FormFieldEntity();
            field4.setName("Status");
            field4.setType(FormFieldType.SELECT);
            field4.setRequired(true);
            field4.setOrder(4);
            field4.setForm(form1);
            field4.setIdentifyingEntity(plants.get(0));

            formFieldRepository.saveAll(List.of(field1, field2, field3, field4));

            FormOptionEntity opt1 = new FormOptionEntity();
            opt1.setValue("Completed");
            opt1.setFormField(field4);
            opt1.setIdentifyingEntity(plants.get(0));

            FormOptionEntity opt2 = new FormOptionEntity();
            opt2.setValue("Pending");
            opt2.setFormField(field4);
            opt2.setIdentifyingEntity(plants.get(0));

            FormOptionEntity opt3 = new FormOptionEntity();
            opt3.setValue("Not Needed");
            opt3.setFormField(field4);
            opt3.setIdentifyingEntity(plants.get(0));

            formOptionRepository.saveAll(List.of(opt1, opt2, opt3));

            // ======================================================================================================

            // Corrective Form:
            FormFieldEntity correctiveField1 = new FormFieldEntity();
            correctiveField1.setName("Failure Description");
            correctiveField1.setType(FormFieldType.TEXTAREA);
            correctiveField1.setRequired(true);
            correctiveField1.setOrder(1);
            correctiveField1.setForm(form2);
            correctiveField1.setIdentifyingEntity(plants.get(0));

            FormFieldEntity correctiveField2 = new FormFieldEntity();
            correctiveField2.setName("Parts Replaced");
            correctiveField2.setType(FormFieldType.TEXT);
            correctiveField2.setRequired(true);
            correctiveField2.setOrder(2);
            correctiveField2.setForm(form2);
            correctiveField2.setIdentifyingEntity(plants.get(0));

            FormFieldEntity correctiveField3 = new FormFieldEntity();
            correctiveField3.setName("Repair Date");
            correctiveField3.setType(FormFieldType.DATE);
            correctiveField3.setRequired(true);
            correctiveField3.setOrder(3);
            correctiveField3.setForm(form2);
            correctiveField3.setIdentifyingEntity(plants.get(0));

            FormFieldEntity correctiveField4 = new FormFieldEntity();
            correctiveField4.setName("Status");
            correctiveField4.setType(FormFieldType.SELECT);
            correctiveField4.setRequired(true);
            correctiveField4.setOrder(4);
            correctiveField4.setForm(form2);
            correctiveField4.setIdentifyingEntity(plants.get(0));

            formFieldRepository.saveAll(List.of(correctiveField1, correctiveField2, correctiveField3, correctiveField4));

            FormOptionEntity correctiveOpt1 = new FormOptionEntity();
            correctiveOpt1.setValue("Fixed");
            correctiveOpt1.setFormField(correctiveField4);
            correctiveOpt1.setIdentifyingEntity(plants.get(0));

            FormOptionEntity correctiveOpt2 = new FormOptionEntity();
            correctiveOpt2.setValue("In Progress");
            correctiveOpt2.setFormField(correctiveField4);
            correctiveOpt2.setIdentifyingEntity(plants.get(0));

            FormOptionEntity correctiveOpt3 = new FormOptionEntity();
            correctiveOpt3.setValue("Deferred");
            correctiveOpt3.setFormField(correctiveField4);
            correctiveOpt3.setIdentifyingEntity(plants.get(0));

            formOptionRepository.saveAll(List.of(correctiveOpt1, correctiveOpt2, correctiveOpt3));

            // ======================================================================================================

            // Inspection Form:
            FormFieldEntity inspectionField1 = new FormFieldEntity();
            inspectionField1.setName("Inspector Name");
            inspectionField1.setType(FormFieldType.TEXT);
            inspectionField1.setRequired(true);
            inspectionField1.setOrder(1);
            inspectionField1.setForm(form3);
            inspectionField1.setIdentifyingEntity(plants.get(0));

            FormFieldEntity inspectionField2 = new FormFieldEntity();
            inspectionField2.setName("Any Visible Damage?");
            inspectionField2.setType(FormFieldType.CHECKBOX);
            inspectionField2.setRequired(false);
            inspectionField2.setOrder(2);
            inspectionField2.setForm(form3);
            inspectionField2.setIdentifyingEntity(plants.get(0));

            FormFieldEntity inspectionField3 = new FormFieldEntity();
            inspectionField3.setName("Inspection Date");
            inspectionField3.setType(FormFieldType.DATE);
            inspectionField3.setRequired(true);
            inspectionField3.setOrder(3);
            inspectionField3.setForm(form3);
            inspectionField3.setIdentifyingEntity(plants.get(0));

            FormFieldEntity inspectionField4 = new FormFieldEntity();
            inspectionField4.setName("Additional Notes");
            inspectionField4.setType(FormFieldType.TEXTAREA);
            inspectionField4.setRequired(true);
            inspectionField4.setOrder(4);
            inspectionField4.setForm(form3);
            inspectionField4.setIdentifyingEntity(plants.get(0));

            formFieldRepository.saveAll(List.of(inspectionField1, inspectionField2, inspectionField3, inspectionField4));

            FormOptionEntity inspectionOpt1 = new FormOptionEntity();
            inspectionOpt1.setValue("Yes");
            inspectionOpt1.setFormField(inspectionField2);
            inspectionOpt1.setIdentifyingEntity(plants.get(0));

            FormOptionEntity inspectionOpt2 = new FormOptionEntity();
            inspectionOpt2.setValue("No");
            inspectionOpt2.setFormField(inspectionField2);
            inspectionOpt2.setIdentifyingEntity(plants.get(0));

            formOptionRepository.saveAll(List.of(inspectionOpt1, inspectionOpt2));

            System.out.println("✅ Formulario de lubricación cargado con campos y opciones.");
        }
    }
}


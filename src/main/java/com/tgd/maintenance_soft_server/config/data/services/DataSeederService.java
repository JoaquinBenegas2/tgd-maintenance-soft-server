package com.tgd.maintenance_soft_server.config.data.services;

import com.tgd.maintenance_soft_server.modules.asset.entities.AssetEntity;
import com.tgd.maintenance_soft_server.modules.asset.models.AssetStatus;
import com.tgd.maintenance_soft_server.modules.asset.repositories.AssetRepository;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.component.repositories.ComponentRepository;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.models.ElementStatus;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormFieldEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormOptionEntity;
import com.tgd.maintenance_soft_server.modules.form.models.FormFieldType;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormFieldRepository;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormOptionRepository;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceAnswerEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceTypeEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceAnswerRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceTypeRepository;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.repositories.ManufacturerRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.plant.repositories.PlantRepository;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import com.tgd.maintenance_soft_server.modules.route.models.RouteStatus;
import com.tgd.maintenance_soft_server.modules.route.repositories.RouteRepository;
import com.tgd.maintenance_soft_server.modules.sector.entities.SectorEntity;
import com.tgd.maintenance_soft_server.modules.sector.repositories.SectorRepository;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataSeederService {

    private final PlantRepository plantRepository;
    private final MaintenanceTypeRepository maintenanceTypeRepository;
    private final FormRepository formRepository;
    private final FormFieldRepository formFieldRepository;
    private final FormOptionRepository formOptionRepository;
    private final SectorRepository sectorRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final AssetRepository assetRepository;
    private final ComponentRepository componentRepository;
    private final ElementRepository elementRepository;
    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceAnswerRepository maintenanceAnswerRepository;

    private List<PlantEntity> plants;
    private List<MaintenanceTypeEntity> maintenanceTypes;

    public void seedDataWithoutMaintenance() {
        deleteAll();

        seedPlants();
        seedMaintenanceTypes();
        seedForms();
        seedSectors();
        seedManufacturers();
        seedAssets();
        seedComponents();
        seedElements();
        seedRoutes();
    }

    public void seedMaintenance() {
        seedPlants();
        seedMaintenances();
    }

    private void seedPlants() {
        plants = plantRepository.findAll();
    }

    private void deleteAll() {
        routeRepository.findAll().forEach(route -> {
            route.getAssignedElements().clear();
        });
        routeRepository.saveAll(routeRepository.findAll());

        maintenanceAnswerRepository.deleteAll();
        maintenanceRepository.deleteAll();
        elementRepository.deleteAll();
        componentRepository.deleteAll();
        assetRepository.deleteAll();
        manufacturerRepository.deleteAll();
        sectorRepository.deleteAll();
        formOptionRepository.deleteAll();
        formFieldRepository.deleteAll();
        formRepository.deleteAll();
        maintenanceTypeRepository.deleteAll();
        routeRepository.deleteAll();
    }

    private void seedMaintenanceTypes() {
        MaintenanceTypeEntity lubrication = new MaintenanceTypeEntity();
        lubrication.setName("Lubrication");
        lubrication.setDescription("Scheduled lubrication of mechanical components to reduce friction and wear.");
        lubrication.setIdentifyingEntity(plants.get(0));

        MaintenanceTypeEntity corrective = new MaintenanceTypeEntity();
        corrective.setName("Corrective Maintenance");
        corrective.setDescription("Repair or replacement of equipment after a detected failure.");
        corrective.setIdentifyingEntity(plants.get(0));

        MaintenanceTypeEntity inspection = new MaintenanceTypeEntity();
        inspection.setName("Routine Inspection");
        inspection.setDescription("Regular visual and functional inspections to identify issues early.");
        inspection.setIdentifyingEntity(plants.get(0));

        MaintenanceTypeEntity calibration = new MaintenanceTypeEntity();
        calibration.setName("Calibration");
        calibration.setDescription("Adjustment and verification of instruments to ensure accurate measurements.");
        calibration.setIdentifyingEntity(plants.get(0));

        maintenanceTypes = List.of(lubrication, corrective, inspection, calibration);
        maintenanceTypeRepository.saveAll(maintenanceTypes);
    }

    private void seedForms() {
        FormEntity lubricationForm = new FormEntity();
        lubricationForm.setName("Lubrication Checklist");
        lubricationForm.setDescription("Checklist for verifying lubrication points and applying lubricants where necessary.");
        lubricationForm.setMaintenanceType(maintenanceTypes.get(0));
        lubricationForm.setIdentifyingEntity(plants.get(0));

        FormEntity quickCheckForm = new FormEntity();
        quickCheckForm.setName("Lubrication Quick Check");
        quickCheckForm.setDescription("Basic checklist for quick verification of lubrication-related conditions.");
        quickCheckForm.setMaintenanceType(maintenanceTypes.get(0));
        quickCheckForm.setIdentifyingEntity(plants.get(0));


        FormEntity correctiveForm = new FormEntity();
        correctiveForm.setName("Corrective Action Report");
        correctiveForm.setDescription("Form used to document faults, corrective actions taken, and parts replaced.");
        correctiveForm.setMaintenanceType(maintenanceTypes.get(1));
        correctiveForm.setIdentifyingEntity(plants.get(0));

        FormEntity inspectionForm = new FormEntity();
        inspectionForm.setName("Visual Inspection Report");
        inspectionForm.setDescription("Report for routine visual inspections, noting wear, leaks, or irregularities.");
        inspectionForm.setMaintenanceType(maintenanceTypes.get(2));
        inspectionForm.setIdentifyingEntity(plants.get(0));

        FormEntity calibrationForm = new FormEntity();
        calibrationForm.setName("Calibration Record Sheet");
        calibrationForm.setDescription("Log for recording calibration procedures, deviations found, and adjustments made.");
        calibrationForm.setMaintenanceType(maintenanceTypes.get(3));
        calibrationForm.setIdentifyingEntity(plants.get(0));

        formRepository.saveAll(List.of(
                lubricationForm,
                quickCheckForm,
                correctiveForm,
                inspectionForm,
                calibrationForm
        ));
        formRepository.flush();

        seedLubricationForm(lubricationForm);
        seedLubricationQuickCheckForm(quickCheckForm);
        seedCorrectiveForm(correctiveForm);
        seedInspectionForm(inspectionForm);
        seedCalibrationForm(calibrationForm);
    }

    private void seedCalibrationForm(FormEntity calibrationForm) {
        FormFieldEntity instrumentName = new FormFieldEntity();
        instrumentName.setName("Instrument Name");
        instrumentName.setType(FormFieldType.TEXT);
        instrumentName.setRequired(true);
        instrumentName.setOrder(1);
        instrumentName.setForm(calibrationForm);
        instrumentName.setIdentifyingEntity(plants.get(0));

        FormFieldEntity calibrationStandard = new FormFieldEntity();
        calibrationStandard.setName("Calibration Standard Used");
        calibrationStandard.setType(FormFieldType.TEXT);
        calibrationStandard.setRequired(true);
        calibrationStandard.setOrder(2);
        calibrationStandard.setForm(calibrationForm);
        calibrationStandard.setIdentifyingEntity(plants.get(0));

        FormFieldEntity deviationDetected = new FormFieldEntity();
        deviationDetected.setName("Deviation Detected?");
        deviationDetected.setType(FormFieldType.SELECT);
        deviationDetected.setRequired(true);
        deviationDetected.setOrder(3);
        deviationDetected.setForm(calibrationForm);
        deviationDetected.setIdentifyingEntity(plants.get(0));

        FormFieldEntity adjustmentMade = new FormFieldEntity();
        adjustmentMade.setName("Adjustment Made?");
        adjustmentMade.setType(FormFieldType.SELECT);
        adjustmentMade.setRequired(true);
        adjustmentMade.setOrder(4);
        adjustmentMade.setForm(calibrationForm);
        adjustmentMade.setIdentifyingEntity(plants.get(0));

        FormFieldEntity calibrationResult = new FormFieldEntity();
        calibrationResult.setName("Calibration Result");
        calibrationResult.setType(FormFieldType.SELECT);
        calibrationResult.setRequired(true);
        calibrationResult.setOrder(5);
        calibrationResult.setForm(calibrationForm);
        calibrationResult.setIdentifyingEntity(plants.get(0));

        FormFieldEntity comments = new FormFieldEntity();
        comments.setName("Comments");
        comments.setType(FormFieldType.TEXTAREA);
        comments.setRequired(false);
        comments.setOrder(6);
        comments.setForm(calibrationForm);
        comments.setIdentifyingEntity(plants.get(0));

        FormFieldEntity calibrationDate = new FormFieldEntity();
        calibrationDate.setName("Calibration Date");
        calibrationDate.setType(FormFieldType.DATE);
        calibrationDate.setRequired(true);
        calibrationDate.setOrder(7);
        calibrationDate.setForm(calibrationForm);
        calibrationDate.setIdentifyingEntity(plants.get(0));

        formFieldRepository.saveAll(List.of(
                instrumentName,
                calibrationStandard,
                deviationDetected,
                adjustmentMade,
                calibrationResult,
                comments,
                calibrationDate
        ));

        // Deviation Detected options
        FormOptionEntity deviationYes = new FormOptionEntity();
        deviationYes.setValue("Yes");
        deviationYes.setFormField(deviationDetected);
        deviationYes.setIdentifyingEntity(plants.get(0));

        FormOptionEntity deviationNo = new FormOptionEntity();
        deviationNo.setValue("No");
        deviationNo.setFormField(deviationDetected);
        deviationNo.setIdentifyingEntity(plants.get(0));

        // Adjustment Made options
        FormOptionEntity adjustmentYes = new FormOptionEntity();
        adjustmentYes.setValue("Yes");
        adjustmentYes.setFormField(adjustmentMade);
        adjustmentYes.setIdentifyingEntity(plants.get(0));

        FormOptionEntity adjustmentNo = new FormOptionEntity();
        adjustmentNo.setValue("No");
        adjustmentNo.setFormField(adjustmentMade);
        adjustmentNo.setIdentifyingEntity(plants.get(0));

        // Calibration Result options
        FormOptionEntity resultPass = new FormOptionEntity();
        resultPass.setValue("Pass");
        resultPass.setFormField(calibrationResult);
        resultPass.setIdentifyingEntity(plants.get(0));

        FormOptionEntity resultFail = new FormOptionEntity();
        resultFail.setValue("Fail");
        resultFail.setFormField(calibrationResult);
        resultFail.setIdentifyingEntity(plants.get(0));

        FormOptionEntity resultRecalibrate = new FormOptionEntity();
        resultRecalibrate.setValue("Needs Recalibration");
        resultRecalibrate.setFormField(calibrationResult);
        resultRecalibrate.setIdentifyingEntity(plants.get(0));

        formOptionRepository.saveAll(List.of(
                deviationYes,
                deviationNo,
                adjustmentYes,
                adjustmentNo,
                resultPass,
                resultFail,
                resultRecalibrate
        ));
    }

    private void seedInspectionForm(FormEntity inspectionForm) {
        FormFieldEntity inspectionArea = new FormFieldEntity();
        inspectionArea.setName("Inspection Area");
        inspectionArea.setType(FormFieldType.TEXT);
        inspectionArea.setRequired(true);
        inspectionArea.setOrder(1);
        inspectionArea.setForm(inspectionForm);
        inspectionArea.setIdentifyingEntity(plants.get(0));

        FormFieldEntity visualCondition = new FormFieldEntity();
        visualCondition.setName("Visual Condition");
        visualCondition.setType(FormFieldType.SELECT);
        visualCondition.setRequired(true);
        visualCondition.setOrder(2);
        visualCondition.setForm(inspectionForm);
        visualCondition.setIdentifyingEntity(plants.get(0));

        FormFieldEntity signsOfWear = new FormFieldEntity();
        signsOfWear.setName("Signs of Wear or Damage");
        signsOfWear.setType(FormFieldType.SELECT);
        signsOfWear.setRequired(true);
        signsOfWear.setOrder(3);
        signsOfWear.setForm(inspectionForm);
        signsOfWear.setIdentifyingEntity(plants.get(0));

        FormFieldEntity leaksDetected = new FormFieldEntity();
        leaksDetected.setName("Leaks Detected");
        leaksDetected.setType(FormFieldType.SELECT);
        leaksDetected.setRequired(true);
        leaksDetected.setOrder(4);
        leaksDetected.setForm(inspectionForm);
        leaksDetected.setIdentifyingEntity(plants.get(0));

        FormFieldEntity correctiveNeeded = new FormFieldEntity();
        correctiveNeeded.setName("Corrective Action Needed");
        correctiveNeeded.setType(FormFieldType.SELECT);
        correctiveNeeded.setRequired(true);
        correctiveNeeded.setOrder(5);
        correctiveNeeded.setForm(inspectionForm);
        correctiveNeeded.setIdentifyingEntity(plants.get(0));

        FormFieldEntity generalObservations = new FormFieldEntity();
        generalObservations.setName("General Observations");
        generalObservations.setType(FormFieldType.TEXTAREA);
        generalObservations.setRequired(false);
        generalObservations.setOrder(6);
        generalObservations.setForm(inspectionForm);
        generalObservations.setIdentifyingEntity(plants.get(0));

        FormFieldEntity inspectionDate = new FormFieldEntity();
        inspectionDate.setName("Inspection Date");
        inspectionDate.setType(FormFieldType.DATE);
        inspectionDate.setRequired(true);
        inspectionDate.setOrder(7);
        inspectionDate.setForm(inspectionForm);
        inspectionDate.setIdentifyingEntity(plants.get(0));

        formFieldRepository.saveAll(List.of(
                inspectionArea,
                visualCondition,
                signsOfWear,
                leaksDetected,
                correctiveNeeded,
                generalObservations,
                inspectionDate
        ));

        // Visual Condition options
        FormOptionEntity goodCondition = new FormOptionEntity();
        goodCondition.setValue("Good");
        goodCondition.setFormField(visualCondition);
        goodCondition.setIdentifyingEntity(plants.get(0));

        FormOptionEntity fairCondition = new FormOptionEntity();
        fairCondition.setValue("Fair");
        fairCondition.setFormField(visualCondition);
        fairCondition.setIdentifyingEntity(plants.get(0));

        FormOptionEntity poorCondition = new FormOptionEntity();
        poorCondition.setValue("Poor");
        poorCondition.setFormField(visualCondition);
        poorCondition.setIdentifyingEntity(plants.get(0));

        // Signs of Wear options
        FormOptionEntity wearYes = new FormOptionEntity();
        wearYes.setValue("Yes");
        wearYes.setFormField(signsOfWear);
        wearYes.setIdentifyingEntity(plants.get(0));

        FormOptionEntity wearNo = new FormOptionEntity();
        wearNo.setValue("No");
        wearNo.setFormField(signsOfWear);
        wearNo.setIdentifyingEntity(plants.get(0));

        // Leaks Detected options
        FormOptionEntity leaksYes = new FormOptionEntity();
        leaksYes.setValue("Yes");
        leaksYes.setFormField(leaksDetected);
        leaksYes.setIdentifyingEntity(plants.get(0));

        FormOptionEntity leaksNo = new FormOptionEntity();
        leaksNo.setValue("No");
        leaksNo.setFormField(leaksDetected);
        leaksNo.setIdentifyingEntity(plants.get(0));

        // Corrective Action Needed options
        FormOptionEntity correctiveYes = new FormOptionEntity();
        correctiveYes.setValue("Yes");
        correctiveYes.setFormField(correctiveNeeded);
        correctiveYes.setIdentifyingEntity(plants.get(0));

        FormOptionEntity correctiveNo = new FormOptionEntity();
        correctiveNo.setValue("No");
        correctiveNo.setFormField(correctiveNeeded);
        correctiveNo.setIdentifyingEntity(plants.get(0));

        formOptionRepository.saveAll(List.of(
                goodCondition,
                fairCondition,
                poorCondition,
                wearYes,
                wearNo,
                leaksYes,
                leaksNo,
                correctiveYes,
                correctiveNo
        ));
    }

    private void seedCorrectiveForm(FormEntity correctiveForm) {
        FormFieldEntity failureDescription = new FormFieldEntity();
        failureDescription.setName("Failure Description");
        failureDescription.setType(FormFieldType.TEXTAREA);
        failureDescription.setRequired(true);
        failureDescription.setOrder(1);
        failureDescription.setForm(correctiveForm);
        failureDescription.setIdentifyingEntity(plants.get(0));

        FormFieldEntity rootCause = new FormFieldEntity();
        rootCause.setName("Root Cause");
        rootCause.setType(FormFieldType.TEXTAREA);
        rootCause.setRequired(false);
        rootCause.setOrder(2);
        rootCause.setForm(correctiveForm);
        rootCause.setIdentifyingEntity(plants.get(0));

        FormFieldEntity correctiveAction = new FormFieldEntity();
        correctiveAction.setName("Corrective Action Taken");
        correctiveAction.setType(FormFieldType.TEXTAREA);
        correctiveAction.setRequired(true);
        correctiveAction.setOrder(3);
        correctiveAction.setForm(correctiveForm);
        correctiveAction.setIdentifyingEntity(plants.get(0));

        FormFieldEntity partsReplaced = new FormFieldEntity();
        partsReplaced.setName("Parts Replaced");
        partsReplaced.setType(FormFieldType.TEXT);
        partsReplaced.setRequired(false);
        partsReplaced.setOrder(4);
        partsReplaced.setForm(correctiveForm);
        partsReplaced.setIdentifyingEntity(plants.get(0));

        FormFieldEntity maintenanceDuration = new FormFieldEntity();
        maintenanceDuration.setName("Duration (minutes)");
        maintenanceDuration.setType(FormFieldType.NUMBER);
        maintenanceDuration.setRequired(true);
        maintenanceDuration.setOrder(5);
        maintenanceDuration.setForm(correctiveForm);
        maintenanceDuration.setIdentifyingEntity(plants.get(0));

        FormFieldEntity dateOfRepair = new FormFieldEntity();
        dateOfRepair.setName("Date of Repair");
        dateOfRepair.setType(FormFieldType.DATE);
        dateOfRepair.setRequired(true);
        dateOfRepair.setOrder(6);
        dateOfRepair.setForm(correctiveForm);
        dateOfRepair.setIdentifyingEntity(plants.get(0));

        FormFieldEntity status = new FormFieldEntity();
        status.setName("Status");
        status.setType(FormFieldType.SELECT);
        status.setRequired(true);
        status.setOrder(7);
        status.setForm(correctiveForm);
        status.setIdentifyingEntity(plants.get(0));

        formFieldRepository.saveAll(List.of(
                failureDescription,
                rootCause,
                correctiveAction,
                partsReplaced,
                maintenanceDuration,
                dateOfRepair,
                status
        ));

        FormOptionEntity resolvedOption = new FormOptionEntity();
        resolvedOption.setValue("Resolved");
        resolvedOption.setFormField(status);
        resolvedOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity unresolvedOption = new FormOptionEntity();
        unresolvedOption.setValue("Unresolved");
        unresolvedOption.setFormField(status);
        unresolvedOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity monitoringOption = new FormOptionEntity();
        monitoringOption.setValue("Under Monitoring");
        monitoringOption.setFormField(status);
        monitoringOption.setIdentifyingEntity(plants.get(0));

        formOptionRepository.saveAll(List.of(
                resolvedOption,
                unresolvedOption,
                monitoringOption
        ));
    }

    private void seedLubricationForm(FormEntity lubricationForm) {
        FormFieldEntity lubricantType = new FormFieldEntity();
        lubricantType.setName("Lubricant Type");
        lubricantType.setType(FormFieldType.SELECT);
        lubricantType.setRequired(true);
        lubricantType.setOrder(1);
        lubricantType.setForm(lubricationForm);
        lubricantType.setIdentifyingEntity(plants.get(0));

        FormFieldEntity quantityApplied = new FormFieldEntity();
        quantityApplied.setName("Quantity Applied");
        quantityApplied.setType(FormFieldType.NUMBER);
        quantityApplied.setRequired(true);
        quantityApplied.setOrder(2);
        quantityApplied.setForm(lubricationForm);
        quantityApplied.setIdentifyingEntity(plants.get(0));

        FormFieldEntity lubricationMethod = new FormFieldEntity();
        lubricationMethod.setName("Lubrication Method");
        lubricationMethod.setType(FormFieldType.SELECT);
        lubricationMethod.setRequired(true);
        lubricationMethod.setOrder(3);
        lubricationMethod.setForm(lubricationForm);
        lubricationMethod.setIdentifyingEntity(plants.get(0));

        FormFieldEntity wasAccessSafe = new FormFieldEntity();
        wasAccessSafe.setName("Was access safe & clear?");
        wasAccessSafe.setType(FormFieldType.SELECT);
        wasAccessSafe.setRequired(true);
        wasAccessSafe.setOrder(4);
        wasAccessSafe.setForm(lubricationForm);
        wasAccessSafe.setIdentifyingEntity(plants.get(0));

        FormFieldEntity observations = new FormFieldEntity();
        observations.setName("Observations");
        observations.setType(FormFieldType.TEXTAREA);
        observations.setRequired(false);
        observations.setOrder(5);
        observations.setForm(lubricationForm);
        observations.setIdentifyingEntity(plants.get(0));

        FormFieldEntity applicationDate = new FormFieldEntity();
        applicationDate.setName("Application Date");
        applicationDate.setType(FormFieldType.DATE);
        applicationDate.setRequired(true);
        applicationDate.setOrder(6);
        applicationDate.setForm(lubricationForm);
        applicationDate.setIdentifyingEntity(plants.get(0));

        FormFieldEntity status = new FormFieldEntity();
        status.setName("Status");
        status.setType(FormFieldType.SELECT);
        status.setRequired(true);
        status.setOrder(7);
        status.setForm(lubricationForm);
        status.setIdentifyingEntity(plants.get(0));

        formFieldRepository.saveAll(List.of(
                lubricantType,
                quantityApplied,
                lubricationMethod,
                wasAccessSafe,
                observations,
                applicationDate,
                status
        ));

        // Lubricant Type options
        FormOptionEntity greaseOption = new FormOptionEntity();
        greaseOption.setValue("Grease");
        greaseOption.setFormField(lubricantType);
        greaseOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity oilOption = new FormOptionEntity();
        oilOption.setValue("Oil");
        oilOption.setFormField(lubricantType);
        oilOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity syntheticOption = new FormOptionEntity();
        syntheticOption.setValue("Synthetic");
        syntheticOption.setFormField(lubricantType);
        syntheticOption.setIdentifyingEntity(plants.get(0));

        // Lubrication Method options
        FormOptionEntity manualOption = new FormOptionEntity();
        manualOption.setValue("Manual");
        manualOption.setFormField(lubricationMethod);
        manualOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity automatic = new FormOptionEntity();
        automatic.setValue("Automatic");
        automatic.setFormField(lubricationMethod);
        automatic.setIdentifyingEntity(plants.get(0));

        FormOptionEntity spray = new FormOptionEntity();
        spray.setValue("Spray");
        spray.setFormField(lubricationMethod);
        spray.setIdentifyingEntity(plants.get(0));

        // Access Safe options
        FormOptionEntity yesOption = new FormOptionEntity();
        yesOption.setValue("Yes");
        yesOption.setFormField(wasAccessSafe);
        yesOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity noOption = new FormOptionEntity();
        noOption.setValue("No");
        noOption.setFormField(wasAccessSafe);
        noOption.setIdentifyingEntity(plants.get(0));

        // Status options
        FormOptionEntity completedOption = new FormOptionEntity();
        completedOption.setValue("Completed");
        completedOption.setFormField(status);
        completedOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity pendingOption = new FormOptionEntity();
        pendingOption.setValue("Pending");
        pendingOption.setFormField(status);
        pendingOption.setIdentifyingEntity(plants.get(0));

        FormOptionEntity notRequiredOption = new FormOptionEntity();
        notRequiredOption.setValue("Not Required");
        notRequiredOption.setFormField(status);
        notRequiredOption.setIdentifyingEntity(plants.get(0));


        formOptionRepository.saveAll(List.of(
                greaseOption,
                oilOption,
                syntheticOption,
                manualOption,
                automatic,
                spray,
                yesOption,
                noOption,
                completedOption,
                pendingOption,
                notRequiredOption
        ));
    }

    private void seedLubricationQuickCheckForm(FormEntity quickCheckForm) {
        FormFieldEntity checkArea = new FormFieldEntity();
        checkArea.setName("Area Checked");
        checkArea.setType(FormFieldType.TEXT);
        checkArea.setRequired(true);
        checkArea.setOrder(1);
        checkArea.setForm(quickCheckForm);
        checkArea.setIdentifyingEntity(plants.get(0));

        FormFieldEntity abnormalNoise = new FormFieldEntity();
        abnormalNoise.setName("Abnormal Noise Detected?");
        abnormalNoise.setType(FormFieldType.SELECT);
        abnormalNoise.setRequired(true);
        abnormalNoise.setOrder(2);
        abnormalNoise.setForm(quickCheckForm);
        abnormalNoise.setIdentifyingEntity(plants.get(0));

        FormFieldEntity comments = new FormFieldEntity();
        comments.setName("Additional Comments");
        comments.setType(FormFieldType.TEXTAREA);
        comments.setRequired(false);
        comments.setOrder(3);
        comments.setForm(quickCheckForm);
        comments.setIdentifyingEntity(plants.get(0));

        formFieldRepository.saveAll(List.of(checkArea, abnormalNoise, comments));

        FormOptionEntity noiseYes = new FormOptionEntity();
        noiseYes.setValue("Yes");
        noiseYes.setFormField(abnormalNoise);
        noiseYes.setIdentifyingEntity(plants.get(0));

        FormOptionEntity noiseNo = new FormOptionEntity();
        noiseNo.setValue("No");
        noiseNo.setFormField(abnormalNoise);
        noiseNo.setIdentifyingEntity(plants.get(0));

        formOptionRepository.saveAll(List.of(noiseYes, noiseNo));
    }


    // 15 sectors:
    private void seedSectors() {
        SectorEntity sectorA = new SectorEntity();
        sectorA.setIdentifyingEntity(plants.get(0));
        sectorA.setActive(true);
        sectorA.setName("Sector A");
        sectorA.setDescription("Main production area in the northern section of the plant.");

        SectorEntity sectorB = new SectorEntity();
        sectorB.setIdentifyingEntity(plants.get(0));
        sectorB.setActive(true);
        sectorB.setName("Sector B");
        sectorB.setDescription("Secondary zone used for complementary processes and storage.");

        SectorEntity pumpZone = new SectorEntity();
        pumpZone.setIdentifyingEntity(plants.get(0));
        pumpZone.setActive(true);
        pumpZone.setName("Pump Zone");
        pumpZone.setDescription("Area where centrifugal and dosing pumps are located.");

        SectorEntity tankArea = new SectorEntity();
        tankArea.setIdentifyingEntity(plants.get(0));
        tankArea.setActive(true);
        tankArea.setName("Tank Area");
        tankArea.setDescription("Outdoor or indoor area with raw and finished product tanks.");

        SectorEntity compressorRoom = new SectorEntity();
        compressorRoom.setIdentifyingEntity(plants.get(0));
        compressorRoom.setActive(true);
        compressorRoom.setName("Compressor Room");
        compressorRoom.setDescription("Dedicated room for air compressors and pneumatic equipment.");

        SectorEntity electricalRoom = new SectorEntity();
        electricalRoom.setIdentifyingEntity(plants.get(0));
        electricalRoom.setActive(true);
        electricalRoom.setName("Electrical Room");
        electricalRoom.setDescription("Room with control panels, switchboards, and electrical protections.");

        SectorEntity boilerRoom = new SectorEntity();
        boilerRoom.setIdentifyingEntity(plants.get(0));
        boilerRoom.setActive(true);
        boilerRoom.setName("Boiler Room");
        boilerRoom.setDescription("Area containing steam generation systems and boilers.");

        SectorEntity packagingArea = new SectorEntity();
        packagingArea.setIdentifyingEntity(plants.get(0));
        packagingArea.setActive(true);
        packagingArea.setName("Packaging Area");
        packagingArea.setDescription("Section where products are packed and labeled.");

        SectorEntity coldZone = new SectorEntity();
        coldZone.setIdentifyingEntity(plants.get(0));
        coldZone.setActive(true);
        coldZone.setName("Cold Zone");
        coldZone.setDescription("Temperature-controlled area used for product preservation.");

        SectorEntity chemicalStorage = new SectorEntity();
        chemicalStorage.setIdentifyingEntity(plants.get(0));
        chemicalStorage.setActive(true);
        chemicalStorage.setName("Chemical Storage");
        chemicalStorage.setDescription("Zone designated for storing chemical substances and inputs.");

        SectorEntity wasteZone = new SectorEntity();
        wasteZone.setIdentifyingEntity(plants.get(0));
        wasteZone.setActive(true);
        wasteZone.setName("Waste Zone");
        wasteZone.setDescription("Area where industrial waste and residues are collected and processed.");

        SectorEntity loadingDock = new SectorEntity();
        loadingDock.setIdentifyingEntity(plants.get(0));
        loadingDock.setActive(true);
        loadingDock.setName("Loading Dock");
        loadingDock.setDescription("Area for loading finished goods onto transport vehicles.");

        SectorEntity unloadingDock = new SectorEntity();
        unloadingDock.setIdentifyingEntity(plants.get(0));
        unloadingDock.setActive(true);
        unloadingDock.setName("Unloading Dock");
        unloadingDock.setDescription("Zone for receiving and inspecting incoming raw materials.");

        SectorEntity mezzanine = new SectorEntity();
        mezzanine.setIdentifyingEntity(plants.get(0));
        mezzanine.setActive(true);
        mezzanine.setName("Mezzanine");
        mezzanine.setDescription("Elevated structure used for installing auxiliary equipment or piping.");

        SectorEntity controlRoom = new SectorEntity();
        controlRoom.setIdentifyingEntity(plants.get(0));
        controlRoom.setActive(true);
        controlRoom.setName("Control Room");
        controlRoom.setDescription("Centralized room for process supervision and monitoring systems.");

        sectorRepository.saveAll(List.of(
                sectorA,
                sectorB,
                pumpZone,
                tankArea,
                compressorRoom,
                electricalRoom,
                boilerRoom,
                packagingArea,
                coldZone,
                chemicalStorage,
                wasteZone,
                loadingDock,
                unloadingDock,
                mezzanine,
                controlRoom
        ));
    }

    // 10 manufacturers:
    private void seedManufacturers() {
        ManufacturerEntity siemens = new ManufacturerEntity();
        siemens.setIdentifyingEntity(plants.get(0));
        siemens.setActive(true);
        siemens.setName("Siemens");
        siemens.setCountry("Germany");

        ManufacturerEntity abb = new ManufacturerEntity();
        abb.setIdentifyingEntity(plants.get(0));
        abb.setActive(true);
        abb.setName("ABB");
        abb.setCountry("Switzerland");

        ManufacturerEntity schneider = new ManufacturerEntity();
        schneider.setIdentifyingEntity(plants.get(0));
        schneider.setActive(true);
        schneider.setName("Schneider Electric");
        schneider.setCountry("France");

        ManufacturerEntity festo = new ManufacturerEntity();
        festo.setIdentifyingEntity(plants.get(0));
        festo.setActive(true);
        festo.setName("Festo");
        festo.setCountry("Germany");

        ManufacturerEntity parker = new ManufacturerEntity();
        parker.setIdentifyingEntity(plants.get(0));
        parker.setActive(true);
        parker.setName("Parker Hannifin");
        parker.setCountry("United States");

        ManufacturerEntity emerson = new ManufacturerEntity();
        emerson.setIdentifyingEntity(plants.get(0));
        emerson.setActive(true);
        emerson.setName("Emerson");
        emerson.setCountry("United States");

        ManufacturerEntity grundfos = new ManufacturerEntity();
        grundfos.setIdentifyingEntity(plants.get(0));
        grundfos.setActive(true);
        grundfos.setName("Grundfos");
        grundfos.setCountry("Denmark");

        ManufacturerEntity yokogawa = new ManufacturerEntity();
        yokogawa.setIdentifyingEntity(plants.get(0));
        yokogawa.setActive(true);
        yokogawa.setName("Yokogawa");
        yokogawa.setCountry("Japan");

        ManufacturerEntity danfoss = new ManufacturerEntity();
        danfoss.setIdentifyingEntity(plants.get(0));
        danfoss.setActive(true);
        danfoss.setName("Danfoss");
        danfoss.setCountry("Denmark");

        ManufacturerEntity smc = new ManufacturerEntity();
        smc.setIdentifyingEntity(plants.get(0));
        smc.setActive(true);
        smc.setName("SMC Corporation");
        smc.setCountry("Japan");

        manufacturerRepository.saveAll(List.of(
                siemens,
                abb,
                schneider,
                festo,
                parker,
                emerson,
                grundfos,
                yokogawa,
                danfoss,
                smc
        ));
    }

    private void seedAssets() {
        List<ManufacturerEntity> manufacturers = manufacturerRepository.findAll();
        List<SectorEntity> sectors = sectorRepository.findAll();

        List<AssetEntity> assets = new ArrayList<>();

        int manufacturerIndex = 0;
        int manufacturerCount[] = new int[manufacturers.size()];

        int assetCounter = 1;

        for (int i = 0; i < sectors.size(); i++) {
            SectorEntity sector = sectors.get(i);

            for (int j = 0; j < 2; j++) {
                while (manufacturerCount[manufacturerIndex] >= 3) {
                    manufacturerIndex = (manufacturerIndex + 1) % manufacturers.size();
                }

                ManufacturerEntity manufacturer = manufacturers.get(manufacturerIndex);
                manufacturerCount[manufacturerIndex]++;

                AssetEntity asset = new AssetEntity();
                asset.setIdentifyingEntity(plants.get(0));
                asset.setStatus(AssetStatus.ACTIVE);
                asset.setSector(sector);
                asset.setManufacturer(manufacturer);
                asset.setInstallationDate(LocalDate.of(2022, 3, 4));

                asset.setName(generateAssetName(assetCounter));
                asset.setModel(generateAssetModel(assetCounter));
                asset.setSerialNumber("SN-" + generateAssetModel(assetCounter).replace(" ", "-").toUpperCase());
                asset.setDescription(generateAssetDescription(assetCounter));

                assets.add(asset);
                assetCounter++;
            }
        }

        assetRepository.saveAll(assets);
    }

    private String generateAssetName(int index) {
        return switch (index) {
            case 1 -> "Centrifugal Pump CPX-300";
            case 2 -> "Air Compressor ACX-120";
            case 3 -> "Electrical Control Panel ECP-400";
            case 4 -> "Vertical Storage Tank VST-5000";
            case 5 -> "Heat Exchanger HEX-220";
            case 6 -> "Pressure Sensor PS-900";
            case 7 -> "Steam Boiler SB-1500";
            case 8 -> "Conveyor Belt CB-700";
            case 9 -> "Flow Meter FM-80";
            case 10 -> "Valve Actuator VA-250";
            case 11 -> "Industrial Mixer MX-310";
            case 12 -> "Chiller Unit CH-950";
            case 13 -> "Gearbox Reducer GR-200";
            case 14 -> "Diesel Generator DG-150";
            case 15 -> "Temperature Sensor TS-330";
            case 16 -> "Hydraulic Press HP-880";
            case 17 -> "Automatic Packaging Machine APM-660";
            case 18 -> "PLC Controller PLC-110";
            case 19 -> "Frequency Inverter FI-75";
            case 20 -> "Load Cell LC-440";
            case 21 -> "Tank Level Sensor TLS-510";
            case 22 -> "Scrubber Fan SF-180";
            case 23 -> "Batch Reactor BR-600";
            case 24 -> "Lighting Panel LP-50";
            case 25 -> "Motor Starter Panel MSP-320";
            case 26 -> "Dryer Unit DR-720";
            case 27 -> "Industrial Oven IO-870";
            case 28 -> "Cooling Tower CT-3500";
            case 29 -> "Dust Collector DC-2000";
            case 30 -> "Weighing Scale WS-180";
            default -> "Equipment " + index;
        };
    }

    private String generateAssetModel(int index) {
        return switch (index) {
            case 1 -> "CPX-300-MKII";
            case 2 -> "ACX-120-V2";
            case 3 -> "ECP-400-A";
            case 4 -> "VST-5000-B";
            case 5 -> "HEX-220-C";
            case 6 -> "PS-900-PRO";
            case 7 -> "SB-1500-ECO";
            case 8 -> "CB-700-LINE";
            case 9 -> "FM-80-PLUS";
            case 10 -> "VA-250-Z";
            case 11 -> "MX-310-R";
            case 12 -> "CH-950-V";
            case 13 -> "GR-200-X";
            case 14 -> "DG-150-DUAL";
            case 15 -> "TS-330-N";
            case 16 -> "HP-880-HD";
            case 17 -> "APM-660-FLEX";
            case 18 -> "PLC-110-IOT";
            case 19 -> "FI-75-BOOST";
            case 20 -> "LC-440-IND";
            case 21 -> "TLS-510-PREC";
            case 22 -> "SF-180-AX";
            case 23 -> "BR-600-TEMP";
            case 24 -> "LP-50-MINI";
            case 25 -> "MSP-320-PRO";
            case 26 -> "DR-720-FAST";
            case 27 -> "IO-870-XL";
            case 28 -> "CT-3500-IND";
            case 29 -> "DC-2000-VAC";
            case 30 -> "WS-180-DIGI";
            default -> "MODEL-X" + index;
        };
    }

    private String generateAssetDescription(int index) {
        return switch (index) {
            case 1 -> "Pumps liquids in closed loop systems with stainless steel housing.";
            case 2 -> "Compresses air for pneumatic tools and process automation.";
            case 3 -> "Central control panel for power distribution and protections.";
            case 4 -> "Vertical tank for storage of raw materials or chemicals.";
            case 5 -> "Exchanger for thermal transfer between process fluids.";
            case 6 -> "Sensor to monitor line pressure with high precision.";
            case 7 -> "Generates steam for thermal processes and sterilization.";
            case 8 -> "Belt system for moving packages to packing stations.";
            case 9 -> "Measures liquid flow in dosing and production lines.";
            case 10 -> "Automated valve actuator with remote control support.";
            case 11 -> "Mixes solid and liquid materials in production batches.";
            case 12 -> "Removes heat from industrial processes for safety.";
            case 13 -> "Reduces gear speed while increasing torque transmission.";
            case 14 -> "Backup power generator for outages and emergencies.";
            case 15 -> "Temperature sensor used in tanks and pipelines.";
            case 16 -> "Applies hydraulic force for pressing and forming tasks.";
            case 17 -> "Automates product packing in high-speed production lines.";
            case 18 -> "Controls and monitors logic sequences in machinery.";
            case 19 -> "Adjusts motor speed and torque based on load demand.";
            case 20 -> "Weighs materials with real-time feedback to controllers.";
            case 21 -> "Monitors tank level with high accuracy ultrasonic sensor.";
            case 22 -> "Extracts and filters airborne particles from exhaust lines.";
            case 23 -> "Controls chemical reactions under pressure and agitation.";
            case 24 -> "Panel with switches and breakers for lighting circuits.";
            case 25 -> "Protects and controls industrial electric motors.";
            case 26 -> "Reduces moisture content in raw materials before processing.";
            case 27 -> "Provides controlled heating for curing or baking processes.";
            case 28 -> "Rejects heat from process water via air-cooled system.";
            case 29 -> "Collects dust from industrial cutting and grinding stations.";
            case 30 -> "Weighs and logs material inputs for traceability.";
            default -> "Industrial asset used in plant operations.";
        };
    }

    private void seedComponents() {
        List<AssetEntity> assets = assetRepository.findAll();
        List<ComponentEntity> components = new ArrayList<>();

        int componentCounter = 1;

        for (int i = 0; i < 8 && i < assets.size(); i++) {
            AssetEntity asset = assets.get(i);
            ManufacturerEntity manufacturer = asset.getManufacturer();

            for (int j = 0; j < 3; j++) {
                ComponentEntity component = new ComponentEntity();
                component.setIdentifyingEntity(plants.get(0));
                component.setAsset(asset);
                component.setManufacturer(manufacturer);
                component.setStatus(ComponentStatus.ACTIVE);
                component.setName(generateComponentName(componentCounter));
                component.setModel(generateComponentModel(componentCounter));
                component.setSerialNumber("CMP-" + String.format("%04d", componentCounter));
                component.setDescription(generateComponentDescription(componentCounter));
                components.add(component);
                componentCounter++;
            }
        }

        componentRepository.saveAll(components);
    }

    private String generateComponentName(int index) {
        return switch (index) {
            case 1 -> "Pressure Regulator";
            case 2 -> "Temperature Probe";
            case 3 -> "Flow Sensor";
            case 4 -> "Control Relay";
            case 5 -> "Solenoid Valve";
            case 6 -> "Proximity Switch";
            case 7 -> "Power Supply Unit";
            case 8 -> "Circuit Breaker";
            case 9 -> "Bearing Assembly";
            case 10 -> "Hydraulic Cylinder";
            case 11 -> "Encoder Module";
            case 12 -> "Level Transmitter";
            case 13 -> "Terminal Block";
            case 14 -> "Fan Motor";
            case 15 -> "Gear Set";
            case 16 -> "Drive Belt";
            case 17 -> "Insulation Pad";
            case 18 -> "Inverter Board";
            case 19 -> "Air Filter";
            case 20 -> "Digital Display";
            case 21 -> "Actuator Arm";
            case 22 -> "Sensor Housing";
            case 23 -> "Fitting Adapter";
            case 24 -> "Communication Module";
            default -> "Component " + index;
        };
    }

    private String generateComponentModel(int index) {
        return switch (index) {
            case 1 -> "PRG-45A";
            case 2 -> "TP-880";
            case 3 -> "FS-210";
            case 4 -> "CR-12X";
            case 5 -> "SV-300";
            case 6 -> "PSW-09";
            case 7 -> "PSU-500W";
            case 8 -> "CBR-25";
            case 9 -> "BA-701";
            case 10 -> "HC-1000";
            case 11 -> "ENC-230R";
            case 12 -> "LT-77";
            case 13 -> "TB-36";
            case 14 -> "FM-145";
            case 15 -> "GS-65";
            case 16 -> "DB-20";
            case 17 -> "IP-03";
            case 18 -> "IB-440";
            case 19 -> "AF-60";
            case 20 -> "DD-120";
            case 21 -> "AA-92";
            case 22 -> "SH-11";
            case 23 -> "FA-16";
            case 24 -> "CM-900";
            default -> "MODEL-C" + index;
        };
    }

    private String generateComponentDescription(int index) {
        return switch (index) {
            case 1 -> "Regulates fluid pressure to maintain system balance.";
            case 2 -> "Measures temperature with high sensitivity.";
            case 3 -> "Senses fluid flow for monitoring and control.";
            case 4 -> "Switches electrical circuits in automation panels.";
            case 5 -> "Controls fluid passage via electromagnetic force.";
            case 6 -> "Detects object presence near process zones.";
            case 7 -> "Converts AC to DC for control systems.";
            case 8 -> "Protects circuits from overload and short-circuit.";
            case 9 -> "Supports rotating shaft in pump or motor.";
            case 10 -> "Generates force in hydraulic operations.";
            case 11 -> "Converts rotation into signal pulses.";
            case 12 -> "Transmits liquid level data for tanks.";
            case 13 -> "Connects wiring inside junction boxes.";
            case 14 -> "Drives cooling fans in electrical enclosures.";
            case 15 -> "Transmits torque within gear mechanisms.";
            case 16 -> "Transfers motion between motor and driven part.";
            case 17 -> "Prevents heat transfer from hot surfaces.";
            case 18 -> "Main power board for motor inverters.";
            case 19 -> "Removes contaminants from intake air.";
            case 20 -> "Displays process values or equipment status.";
            case 21 -> "Moves valve or mechanical link on command.";
            case 22 -> "Protects internal sensor components.";
            case 23 -> "Connects two pipe sections securely.";
            case 24 -> "Provides digital connectivity with PLCs.";
            default -> "Industrial component for system functionality.";
        };
    }

    private void seedElements() {
        List<ComponentEntity> components = componentRepository.findAll();
        List<ElementEntity> elements = new ArrayList<>();

        int elementCounter = 1;

        for (int i = 0; i < 10 && i < components.size(); i++) {
            ComponentEntity component = components.get(i);
            ManufacturerEntity manufacturer = component.getManufacturer();

            for (int j = 0; j < 4; j++) {
                ElementEntity element = new ElementEntity();
                element.setIdentifyingEntity(plants.get(0));
                element.setComponent(component);
                element.setManufacturer(manufacturer);
                element.setStatus(ElementStatus.ACTIVE);
                element.setName(generateElementName(elementCounter));
                element.setDescription(generateElementDescription(elementCounter));
                elements.add(element);
                elementCounter++;
            }
        }

        elementRepository.saveAll(elements);
    }

    private String generateElementName(int index) {
        return switch (index) {
            case 1 -> "O-ring Seal";
            case 2 -> "Temperature Probe Tip";
            case 3 -> "Connector Pin";
            case 4 -> "Relay Coil";
            case 5 -> "Valve Diaphragm";
            case 6 -> "Sensor Cable";
            case 7 -> "Power Terminal";
            case 8 -> "Breaker Spring";
            case 9 -> "Bearing Roller";
            case 10 -> "Cylinder Rod";
            case 11 -> "Encoder Disc";
            case 12 -> "Float Switch";
            case 13 -> "Screw Terminal";
            case 14 -> "Fan Blade";
            case 15 -> "Gear Tooth";
            case 16 -> "Belt Clip";
            case 17 -> "Insulation Sheet";
            case 18 -> "Control Chip";
            case 19 -> "Filter Mesh";
            case 20 -> "LCD Segment";
            case 21 -> "Actuator Shaft";
            case 22 -> "Sensor Cover";
            case 23 -> "Adapter Ring";
            case 24 -> "Communication Port";
            case 25 -> "Mounting Bracket";
            case 26 -> "Spring Washer";
            case 27 -> "LED Indicator";
            case 28 -> "Pressure Pad";
            case 29 -> "Capacitor";
            case 30 -> "Temperature Fuse";
            case 31 -> "Sealing Gasket";
            case 32 -> "Wire Clamp";
            case 33 -> "Alignment Pin";
            case 34 -> "Drain Plug";
            case 35 -> "Ball Bearing";
            case 36 -> "Cooling Fin";
            case 37 -> "Sensor Magnet";
            case 38 -> "Flow Restrictor";
            case 39 -> "PCB Terminal";
            case 40 -> "Optical Lens";
            default -> "Element " + index;
        };
    }

    private String generateElementDescription(int index) {
        return switch (index) {
            case 1 -> "Rubber seal to prevent fluid leakage.";
            case 2 -> "Sensitive metal tip for accurate temp sensing.";
            case 3 -> "Pin used in multi-contact connectors.";
            case 4 -> "Electromagnetic coil to activate relay.";
            case 5 -> "Flexible membrane to control valve pressure.";
            case 6 -> "Shielded cable for sensor signal transmission.";
            case 7 -> "Terminal point for power connections.";
            case 8 -> "Spring mechanism for breaker reset.";
            case 9 -> "Steel roller in bearing assembly.";
            case 10 -> "Polished rod inside hydraulic cylinder.";
            case 11 -> "Disc with slots for optical encoding.";
            case 12 -> "Switch triggered by liquid level change.";
            case 13 -> "Block to secure wire with screw.";
            case 14 -> "Plastic blade for fan ventilation.";
            case 15 -> "Single tooth in industrial gear.";
            case 16 -> "Clip used to secure drive belts.";
            case 17 -> "Thermal insulation for internal surfaces.";
            case 18 -> "Microcontroller chip for logic control.";
            case 19 -> "Fine mesh to capture debris from airflow.";
            case 20 -> "Individual segment in LCD screen.";
            case 21 -> "Metal shaft connected to actuator.";
            case 22 -> "Plastic or metal cover for sensor protection.";
            case 23 -> "Metal ring used to adapt fittings.";
            case 24 -> "RS-485 or Ethernet port for communication.";
            case 25 -> "Bracket to fix component onto panel.";
            case 26 -> "Washer with spring properties for tension.";
            case 27 -> "LED light for status indication.";
            case 28 -> "Rubber pad to sense pressure levels.";
            case 29 -> "Electrolytic capacitor for circuit stability.";
            case 30 -> "Safety fuse that reacts to temperature rise.";
            case 31 -> "Gasket ensuring a tight seal between joints.";
            case 32 -> "Clamp used to hold wires in place.";
            case 33 -> "Metal pin for precision alignment.";
            case 34 -> "Plug used to drain fluids from equipment.";
            case 35 -> "High-speed steel bearing ball.";
            case 36 -> "Aluminum fin to dissipate heat.";
            case 37 -> "Magnetic component used in sensor detection.";
            case 38 -> "Device to control fluid or gas flow.";
            case 39 -> "Connector on printed circuit board.";
            case 40 -> "Lens used to focus light or laser beam.";
            default -> "Functional element for component operation.";
        };
    }

    private void seedRoutes() {
        List<ElementEntity> elements = elementRepository.findAll();
        List<UserEntity> operators = userRepository.findAll().stream()
                .filter(u -> Objects.equals(u.getRole(), "PLANT_OPERATOR"))
                .toList();

        LocalDate startDate = LocalDate.now().minusMonths(1);

        RouteEntity dailyLubrication = new RouteEntity();
        dailyLubrication.setIdentifyingEntity(plants.get(0));
        dailyLubrication.setName("Daily Lubrication Check");
        dailyLubrication.setDescription("Daily route to verify lubrication points and apply lubricant as needed.");
        dailyLubrication.setPeriodicityInDays(1);
        dailyLubrication.setStartDate(startDate);
        dailyLubrication.setStatus(RouteStatus.ACTIVE);
        dailyLubrication.setAssignedElements(elements.subList(0, 5));
        dailyLubrication.setAssignedOperators(operators.subList(0, Math.min(3, operators.size())));

        RouteEntity weeklyInspection = new RouteEntity();
        weeklyInspection.setIdentifyingEntity(plants.get(0));
        weeklyInspection.setName("Weekly Visual Inspection");
        weeklyInspection.setDescription("Weekly routine inspection to detect early signs of wear, leaks, or anomalies.");
        weeklyInspection.setPeriodicityInDays(7);
        weeklyInspection.setStartDate(startDate);
        weeklyInspection.setStatus(RouteStatus.ACTIVE);
        weeklyInspection.setAssignedElements(elements.subList(0, 5));
        weeklyInspection.setAssignedOperators(operators.subList(0, Math.min(3, operators.size())));

        RouteEntity biweeklyCalibration = new RouteEntity();
        biweeklyCalibration.setIdentifyingEntity(plants.get(0));
        biweeklyCalibration.setName("Biweekly Calibration Check");
        biweeklyCalibration.setDescription("Calibration route to verify measurement instruments and adjust if required.");
        biweeklyCalibration.setPeriodicityInDays(14);
        biweeklyCalibration.setStartDate(startDate);
        biweeklyCalibration.setStatus(RouteStatus.ACTIVE);
        biweeklyCalibration.setAssignedElements(elements.subList(10, 15));
        biweeklyCalibration.setAssignedOperators(operators.subList(0, Math.min(3, operators.size())));

        RouteEntity monthlyInspection = new RouteEntity();
        monthlyInspection.setIdentifyingEntity(plants.get(0));
        monthlyInspection.setName("Monthly Preventive Tasks");
        monthlyInspection.setDescription("Planned route to perform preventive maintenance across critical equipment.");
        monthlyInspection.setPeriodicityInDays(30);
        monthlyInspection.setStartDate(startDate);
        monthlyInspection.setStatus(RouteStatus.ACTIVE);
        monthlyInspection.setAssignedElements(elements.subList(15, 20));
        monthlyInspection.setAssignedOperators(operators.subList(0, Math.min(3, operators.size())));

        RouteEntity quarterlyReview = new RouteEntity();
        quarterlyReview.setIdentifyingEntity(plants.get(0));
        quarterlyReview.setName("Quarterly Maintenance Review");
        quarterlyReview.setDescription("Deep maintenance review including inspection, calibration and replacement.");
        quarterlyReview.setPeriodicityInDays(90);
        quarterlyReview.setStartDate(startDate);
        quarterlyReview.setStatus(RouteStatus.ACTIVE);
        quarterlyReview.setAssignedElements(elements.subList(20, 25));
        quarterlyReview.setAssignedOperators(operators.subList(0, Math.min(3, operators.size())));

        routeRepository.saveAll(List.of(
                dailyLubrication,
                weeklyInspection,
                biweeklyCalibration,
                monthlyInspection,
                quarterlyReview
        ));
    }

    private Map<String, List<LocalDate>> generateMaintenanceDates(Map<String, RouteEntity> routeMap) {
        LocalDate today = LocalDate.now();
        Map<String, List<LocalDate>> datesMap = new HashMap<>();

        for (Map.Entry<String, RouteEntity> entry : routeMap.entrySet()) {
            String key = entry.getKey();
            RouteEntity route = entry.getValue();

            List<LocalDate> dates = new ArrayList<>();
            LocalDate date = route.getStartDate();

            while (date.isBefore(today)) {
                dates.add(date);
                date = date.plusDays(route.getPeriodicityInDays());
            }

            datesMap.put(key, dates);
        }

        return datesMap;
    }

    private void seedMaintenances() {
        List<RouteEntity> routes = routeRepository.findAll();
        List<FormEntity> forms = formRepository.findAll();

        Map<String, RouteEntity> routeMap = Map.of(
                "daily", routes.get(0),
                "weekly", routes.get(1),
                "biweekly", routes.get(2),
                "monthly", routes.get(3),
                "quarterly", routes.get(4)
        );

        Map<String, FormEntity> formMap = Map.of(
                "daily", forms.get(0),       // Lubrication
                "weekly", forms.get(3),      // Inspection
                "biweekly", forms.get(4),    // Calibration
                "monthly", forms.get(3),     // Inspection
                "quarterly", forms.get(2)    // Corrective
        );

        Map<String, List<LocalDate>> maintenanceDates = generateMaintenanceDates(routeMap);

        List<MaintenanceEntity> allMaintenances = new ArrayList<>();

        maintenanceDates.forEach((key, dates) -> {
            RouteEntity route = routeMap.get(key);
            FormEntity form = formMap.get(key);

            for (LocalDate date : dates) {
                for (ElementEntity element : route.getAssignedElements()) {
                    MaintenanceEntity maintenance = new MaintenanceEntity();
                    maintenance.setRoute(route);
                    maintenance.setElement(element);
                    maintenance.setMaintenanceDate(date);
                    maintenance.setIdentifyingEntity(plants.get(0));

                    List<MaintenanceAnswerEntity> answers = new ArrayList<>();

                    for (FormFieldEntity field : form.getFields()) {
                        MaintenanceAnswerEntity answer = new MaintenanceAnswerEntity();
                        answer.setMaintenance(maintenance);
                        answer.setForm(form);
                        answer.setFormField(field);
                        answer.setValue(generateAnswer(field.getName(), key, date));
                        answers.add(answer);
                    }

                    maintenance.setAnswers(answers); // 👈 Relación establecida

                    allMaintenances.add(maintenance); // 👈 Agrego solo el mantenimiento
                }
            }
        });

        maintenanceRepository.saveAll(allMaintenances); // 👈 Solo se guarda el mantenimiento, gracias al cascade se guardan también los answers
    }


    private String generateAnswer(String fieldName, String routeType, LocalDate maintenanceDate) {
        return switch (fieldName) {
            case "Lubricant Type" -> List.of("Grease", "Oil", "Synthetic").get((int) (Math.random() * 3));
            case "Quantity Applied" -> String.valueOf((int) (Math.random() * 50 + 10));
            case "Lubrication Method" -> List.of("Manual", "Automatic", "Spray").get((int) (Math.random() * 3));
            case "Was access safe & clear?" -> Math.random() > 0.2 ? "Yes" : "No";
            case "Observations" -> "Routine check, all OK";
            case "Application Date", "Inspection Date", "Date of Repair", "Calibration Date" ->
                    maintenanceDate.toString();
            case "Status" -> List.of("Completed", "Pending", "Not Required").get((int) (Math.random() * 3));
            case "Calibration Result" -> List.of("Pass", "Fail", "Needs Recalibration").get((int) (Math.random() * 3));

            case "Inspection Area" -> "Motor Room";
            case "Visual Condition" -> List.of("Good", "Fair", "Poor").get((int) (Math.random() * 3));
            case "Signs of Wear or Damage", "Leaks Detected", "Corrective Action Needed" ->
                    Math.random() > 0.7 ? "Yes" : "No";
            case "General Observations" -> "No significant issues.";

            case "Instrument Name" -> "Pressure Sensor #" + (int) (Math.random() * 50);
            case "Calibration Standard Used" -> "ISO-9001 Gauge";
            case "Deviation Detected?", "Adjustment Made?" -> Math.random() > 0.6 ? "Yes" : "No";
            case "Comments" -> "Values within acceptable range.";

            case "Failure Description" -> "Loose connection in wiring.";
            case "Root Cause" -> "Wear due to vibration.";
            case "Corrective Action Taken" -> "Tightened and isolated wiring.";
            case "Parts Replaced" -> "Insulation tape";
            case "Duration (minutes)" -> String.valueOf((int) (Math.random() * 60 + 10));
            default -> "-";
        };
    }
}

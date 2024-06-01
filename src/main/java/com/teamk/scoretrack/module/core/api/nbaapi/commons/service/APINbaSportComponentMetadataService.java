package com.teamk.scoretrack.module.core.api.nbaapi.commons.service;

import com.teamk.scoretrack.module.core.api.commons.base.BundleResponse;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentMetadata;
import com.teamk.scoretrack.module.core.api.commons.sport_components.dto.SportComponentsMetadata;
import com.teamk.scoretrack.module.core.api.commons.sport_components.service.ISportComponentMetadataService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaEmptyHelpData;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaHelpData;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.dto.APINbaSportComponentsMetadata;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.convert.APINbaComponentMetadataConvertService;
import com.teamk.scoretrack.module.core.api.nbaapi.commons.service.i18n.APINbaSportComponentTranslatorService;
import com.teamk.scoretrack.module.core.entities.SportAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class APINbaSportComponentMetadataService implements ISportComponentMetadataService {
    private final APINbaUpdateEntityService updateEntityService;
    private final APINbaComponentMetadataConvertService convertService;
    private final APINbaSportComponentTranslatorService translatorService;
    private final Map<String, IAPINbaHelpDataCollectService> helpDataServices;

    @Autowired
    public APINbaSportComponentMetadataService(APINbaUpdateEntityService updateEntityService,
                                               APINbaComponentMetadataConvertService convertService,
                                               APINbaSportComponentTranslatorService translatorService,
                                               List<IAPINbaHelpDataCollectService> helpDataServices) {
        this.updateEntityService = updateEntityService;
        this.convertService = convertService;
        this.translatorService = translatorService;
        this.helpDataServices = helpDataServices.stream()
                .collect(Collectors.toUnmodifiableMap(IAPINbaHelpDataCollectService::getComponentName, Function.identity()));
    }

    @Override
    public SportComponentsMetadata collect() {
        Supplier<Stream<SportComponentMetadata>> sportComponentMetadataStream = () ->
                updateEntityService.getDistinctCollectionsStatuses().stream().map(convertService::toDto);
        Function<SportComponentMetadata, String> getName = SportComponentMetadata::getName;
        return new APINbaSportComponentsMetadata(
                getComponents(sportComponentMetadataStream, getName),
                getHelpText(sportComponentMetadataStream, getName),
                getHelpData(sportComponentMetadataStream, getName)
        );
    }

    private static Map<String, SportComponentMetadata> getComponents(Supplier<Stream<SportComponentMetadata>> sportComponentMetadataStream, Function<SportComponentMetadata, String> getName) {
        return sportComponentMetadataStream.get().collect(Collectors.toMap(getName, Function.identity()));
    }

    private Map<String, BundleResponse> getHelpText(Supplier<Stream<SportComponentMetadata>> sportComponentMetadataStream, Function<SportComponentMetadata, String> getName) {
        Map<String, BundleResponse> helpText = sportComponentMetadataStream.get().filter(isAccessible()).collect(Collectors.toMap(getName, s -> new BundleResponse(translatorService.getMessages(s.getName()))));
        final String commons = "commons";
        helpText.put(commons, new BundleResponse(translatorService.getMessages(commons)));
        return helpText;
    }

    private Map<String, APINbaHelpData> getHelpData(Supplier<Stream<SportComponentMetadata>> sportComponentMetadataStream, Function<SportComponentMetadata, String> getName) {
        return sportComponentMetadataStream.get().filter(isAccessible()).collect(Collectors.toMap(getName, s -> {
            IAPINbaHelpDataCollectService collectService = helpDataServices.get(s.getName());
            return collectService != null ? collectService.getHelpData() : new APINbaEmptyHelpData();
        }));
    }

    private static Predicate<SportComponentMetadata> isAccessible() {
        return s -> s.getStatus().isAccessible();
    }

    @Override
    public int getApiCode() {
        return SportAPI.API_NBA.getCode();
    }
}

/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.muzima.api.service;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.muzima.model.ReportConfiguration;

import java.util.List;

public interface MuzimaReportConfigurationService extends OpenmrsService {

    ReportConfiguration getReportConfigurationById(final Integer id);

    ReportConfiguration getReportConfigurationByUuid(final String uuid);

    ReportConfiguration getReportConfigurationByReportId(final String reportId);

    ReportConfiguration saveReportConfiguration(ReportConfiguration reportConfiguration);

    void deleteReportConfiguration(ReportConfiguration reportConfiguration);

    List<ReportConfiguration> getAllReportConfigurations();

    Number countReportConfigurations();

    /**
     * Get the total number of the settings in the database with partial matching search term.
     *
     *
     * @param search the search term.
     * @return the total number of the settings in the database.
     */
    Number countDataSource(final String search);

    /**
     * Get settings with matching search term for a particular page.
     *
     * @param search     the search term.
     * @param pageNumber the page number.
     * @param pageSize   the size of the page.
     * @return list of all settings with matching search term for a particular page.
     */
    List<ReportConfiguration> getPagedSettings(final String search, final Integer pageNumber, final Integer pageSize);

}

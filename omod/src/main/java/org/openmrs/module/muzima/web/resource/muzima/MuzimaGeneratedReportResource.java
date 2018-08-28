package org.openmrs.module.muzima.web.resource.muzima;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.annotation.Handler;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.muzima.api.service.MuzimaGeneratedReportService;
import org.openmrs.module.muzima.model.MuzimaGeneratedReport;
import org.openmrs.module.muzima.web.controller.MuzimaConstants;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.RefRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Resource(name = MuzimaConstants.MUZIMA_NAMESPACE + "/muzimageneratedreport",
        supportedClass = MuzimaGeneratedReport.class, supportedOpenmrsVersions = {"1.8.*", "1.9.*","1.10.*","1.11.*","1.12.*","2.0.*","2.1.*"})
@Handler(supports = MuzimaGeneratedReport.class)
public class MuzimaGeneratedReportResource extends MetadataDelegatingCrudResource<MuzimaGeneratedReport> {
    private static final Log log = LogFactory.getLog(MuzimaGeneratedReportResource.class);

    @Override
    protected NeedsPaging<MuzimaGeneratedReport> doGetAll(RequestContext context) throws ResponseException {
        MuzimaGeneratedReportService service = Context.getService(MuzimaGeneratedReportService.class);
        List<MuzimaGeneratedReport> all = service.getAllMuzimaGeneratedReports();
        System.out.println("1111111111111111111111111111");
        return new NeedsPaging<MuzimaGeneratedReport>(all, context);
    }
   //ToDo
    @Override
    protected PageableResult doSearch(final RequestContext context) {
        System.out.println("22222222222222222222222222");
        HttpServletRequest request = context.getRequest();
        Integer startIndex = context.getStartIndex();
        Integer limit =  context.getLimit();;
        System.out.println("333333333333333333333");

        String nameParameter = request.getParameter("patientUuid");
        List<MuzimaGeneratedReport> muzimaGeneratedReports = new ArrayList<MuzimaGeneratedReport>();

        if (nameParameter != null) {
            System.out.println("444444444444444444444444444");
            PatientService patientService = Context.getService(PatientService.class);
            Patient patient = patientService.getPatientByUuid(nameParameter);
            MuzimaGeneratedReportService service = Context.getService(MuzimaGeneratedReportService.class);
            System.out.println("pppppppppppppppppppppppppppppppp"+patient.getId()+nameParameter);
            muzimaGeneratedReports.add(Context.getService(MuzimaGeneratedReportService.class).getLastPriorityMuzimaGeneratedReportByPatientId(patient.getId()));
            System.out.println("ccccccccccccccccccccc"+muzimaGeneratedReports.get(0).getReportJson().toString());
        }
        return new NeedsPaging<MuzimaGeneratedReport>(muzimaGeneratedReports, context);
    }

    @Override
    public MuzimaGeneratedReport getByUniqueId(String uuid) {
        System.out.println("333333333333333333");
        System.out.println("llllllllllllllllllllllllllllllll"+uuid);
        PatientService patientService = Context.getService(PatientService.class);
        Patient patient = patientService.getPatientByUuid(uuid);
        MuzimaGeneratedReportService service = Context.getService(MuzimaGeneratedReportService.class);
        System.out.println("pppppppppppppppppppppppppppppppp"+patient.getId());
        return service.getLastPriorityMuzimaGeneratedReportByPatientId(patient.getId());
    }

    //ToDo    
    @Override
    public Object retrieve(String uuid, RequestContext context) throws ResponseException {
        System.out.println("singlesinglesinglesinglesinglesinglesinglesinglesinglesingle");
        MuzimaGeneratedReportService service = Context.getService(MuzimaGeneratedReportService.class);
        PatientService patientService = Context.getService(PatientService.class);
        Patient patient = patientService.getPatientByUuid(uuid);
        return asRepresentation(service.getLastPriorityMuzimaGeneratedReportByPatientId(patient.getId()), context.getRepresentation());
    }
    
    //ToDo 
    @Override
    public void delete(MuzimaGeneratedReport muzimaGeneratedReport, String s, RequestContext requestContext) throws ResponseException {
        throw new ResourceDoesNotSupportOperationException();
    }
    
    //ToDo 
    @Override
    public void purge(MuzimaGeneratedReport muzimaGeneratedReport, RequestContext requestContext) throws ResponseException {
        
    }
    
    public MuzimaGeneratedReport newDelegate() {
        return new MuzimaGeneratedReport();
    }

    @Override
    public MuzimaGeneratedReport save(MuzimaGeneratedReport muzimaGeneratedReport) {
        MuzimaGeneratedReportService service = Context.getService(MuzimaGeneratedReportService.class);
        try {
            return service.saveMuzimaGeneratedReport(muzimaGeneratedReport);
        } catch (Exception e) {
            log.error(e);
        }
        return muzimaGeneratedReport;
    }
    
    //ToDo 
    public DelegatingResourceDescription getRepresentationDescription(final Representation rep) {
        DelegatingResourceDescription description = null;

        //if (rep instanceof DefaultRepresentation || rep instanceof RefRepresentation) {
            description = new DelegatingResourceDescription();
            description.addProperty("uuid");
            description.addProperty("id");
            description.addProperty("patientId");
            description.addProperty("patientUuid");
            description.addProperty("name");
            description.addProperty("description");
            description.addProperty("reportJson");
            description.addSelfLink();
        //}
        System.out.println("vvvvvvvvvvvvvvvvvvvvvv"+description);
        return description;
    }
}

package com.qmt.besedo.controller;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.service.export.ExportReportService;
import com.qmt.besedo.service.inject.InjectMessageService;
import com.qmt.besedo.service.search.SearchMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Entry point to rest call.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ApplicationController {

    private final InjectMessageService injectMessageService;
    private final SearchMessageService searchMessageService;
    private final ExportReportService exportReportService;

    /**
     *
     * @param message the {@link Message} to insert to the database
     * @return Response with a confirmation message
     */
    @PostMapping("/messages")
    public ResponseEntity<Response> injectMessage(@RequestBody Message message) {
        return injectMessageService.inject(message);
    }

    /**
     *
     * @param attribute the {@link MessageAttributeName} involved to filter the query
     * @param operator the {@link SearchOperator}
     * @param value the filter value
     * @param pageIndex the index of page to return, starts from 0. Default value is 0.
     * @param pageSize the  nb of elements to return per page. Default value is 100
     * @return Response containing the list of results using a pagination
     */
    @GetMapping("/messages")
    public ResponseEntity<Response> getMails(@RequestParam MessageAttributeName attribute,
                                             @RequestParam SearchOperator operator,
                                             @RequestParam String value,
                                             @RequestParam(defaultValue = "0") int pageIndex,
                                             @RequestParam(defaultValue = "100") int pageSize) {
        var pageRequest = PageRequest.of(pageIndex, pageSize);
        return searchMessageService.getMessages(attribute, operator, value, pageRequest);
    }

    @GetMapping("/reports/csv")
    public ResponseEntity<ByteArrayResource> getCSVReport() {
        return exportReportService.getCVSReport();
    }

}

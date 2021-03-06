/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hawkular.qe.rest.test.inventory.unittest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.hawkular.qe.rest.inventory.InventoryTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ReachableTest extends InventoryTestBase {
    private static final Logger _logger = LoggerFactory.getLogger(ReachableTest.class);
    private static final String DATE_FORMAT = "EEE MMM dd hh:mm:ss z yyyy";

    @Test(priority = 1)
    public void pingHelloTest() {
        String pingResult = getHawkularClient().inventory().pingHello().getEntity().getDocumentation();
        _logger.debug("Hawkular Inventory Ping Hello response:[{}]", pingResult);
        Assert.assertEquals(pingResult, "http://www.hawkular.org/");
    }

    @Test(priority = 2)
    public void pingTimeTest() {
        String pingResult = getHawkularClient().inventory().pingTime().getEntity();
        _logger.debug("Hawkular Inventory Ping Time response:[{}]", pingResult);
        try {
            //Format: Thu Apr 02 17:31:10 IST 2015
            //TODO: showing wrong time after parsed, fix this
            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date date = dateFormat.parse(pingResult);
            _logger.debug("Converted as date object, Time in milliseconds:[{}], in date:[{}], received time[{}]",
                    date.getTime(), dateFormat.format(date), pingResult);
        } catch (ParseException ex) {
            _logger.debug("Exception while converting string to date: {}", ex.getMessage());
            Assert.fail("unable to convert as timestamp, Received string: " + pingResult);
        }
    }
}
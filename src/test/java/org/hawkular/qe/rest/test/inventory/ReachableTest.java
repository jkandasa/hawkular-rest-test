package org.hawkular.qe.rest.test.inventory;

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

    @Test(priority = 1)
    public void pingHelloTest() {
        String pingResult = getHawkularClient().inventory().pingHello();
        _logger.debug("Hawkular Inventory Ping Hello response:[{}]", pingResult);
        Assert.assertEquals("Hawkular Inventory", pingResult);
    }

    @Test(priority = 2)
    public void pingTimeTest() {
        String pingResult = getHawkularClient().inventory().pingTime();
        _logger.debug("Hawkular Inventory Ping Time response:[{}]", pingResult);
        try {
            //Format: Thu Apr 02 17:31:10 IST 2015
            //TODO: showing wrong time after parsed, fix this
            Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z YYYY").parse(pingResult);
            _logger.debug("Converted as date object, Time in milliseconds:[{}], in date:[{}]", date.getTime(), date);
        } catch (ParseException ex) {
            _logger.debug("Exception while converting string to date: {}", ex.getMessage());
            Assert.fail("unable to convert as timestamp, Received string: " + pingResult);
        }
    }
}
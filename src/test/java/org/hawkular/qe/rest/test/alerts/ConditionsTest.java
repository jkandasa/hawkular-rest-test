/*
 * Copyright 2015-2016 Red Hat, Inc. and/or its affiliates
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
package org.hawkular.qe.rest.test.alerts;

import java.util.ArrayList;
import java.util.List;

import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.condition.CompareCondition;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.condition.StringCondition;
import org.hawkular.alerts.api.model.condition.ThresholdCondition;
import org.hawkular.alerts.api.model.condition.ThresholdCondition.Operator;
import org.hawkular.alerts.api.model.condition.ThresholdRangeCondition;
import org.hawkular.alerts.api.model.data.Data;
import org.hawkular.alerts.api.model.event.Alert;
import org.hawkular.alerts.api.model.trigger.Match;
import org.hawkular.alerts.api.model.trigger.Mode;
import org.hawkular.alerts.api.model.trigger.Trigger;
import org.hawkular.client.alert.model.AlertsParams;
import org.hawkular.qe.rest.alerts.ValidateConditions;
import org.hawkular.qe.rest.alerts.model.ConditionsModel;
import org.hawkular.qe.rest.model.RandomAvailability;
import org.hawkular.qe.rest.model.RandomDouble;
import org.hawkular.qe.rest.model.RandomString;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ConditionsTest extends ValidateConditions {
    public static final long delayTime = 1000;

    public static final double doubleMinValue = 0.0;
    public static final double doubleMaxValue = 1000000.0;

    public static final int dataCountMin = 2;
    public static final int dataCountMax = 30;

    @Test(priority = 0)
    public void testThresholdConditionGT() {
        testThresholdCondition(Operator.GT, Match.ANY);
    }

    @Test(priority = 0)
    public void testThresholdConditionGTE() {
        testThresholdCondition(Operator.GTE, Match.ANY);
    }

    @Test(priority = 0)
    public void testThresholdConditionLT() {
        testThresholdCondition(Operator.LT, Match.ANY);
    }

    @Test(priority = 0)
    public void testThresholdConditionLTE() {
        testThresholdCondition(Operator.LTE, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLinHinRin() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                true, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLinHexRin() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                true, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLexHexRin() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                true, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLexHinRin() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                true, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLinHinRout() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                false, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLinHexRout() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                false, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLexHexRout() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                false, Match.ANY);
    }

    @Test(priority = 1)
    public void testThresholdRangeConditionLexHinRout() {
        testThresholdRangeCondition(
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.EXCLUSIVE,
                org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator.INCLUSIVE,
                false, Match.ANY);
    }

    @Test(priority = 2)
    public void testAvailabilityUp() {
        testAvailabilityCondition(
                org.hawkular.alerts.api.model.condition.AvailabilityCondition.Operator.UP,
                Match.ANY);
    }

    @Test(priority = 2)
    public void testAvailabilityDown() {
        testAvailabilityCondition(
                org.hawkular.alerts.api.model.condition.AvailabilityCondition.Operator.DOWN,
                Match.ANY);
    }

    @Test(priority = 2)
    public void testAvailabilityNotUp() {
        testAvailabilityCondition(
                org.hawkular.alerts.api.model.condition.AvailabilityCondition.Operator.NOT_UP,
                Match.ANY);
    }

    @Test(priority = 3)
    public void testCompareConditionGT() {
        testCompareCondition(org.hawkular.alerts.api.model.condition.CompareCondition.Operator.GT, Match.ANY);
    }

    @Test(priority = 3)
    public void testCompareConditionGTE() {
        testCompareCondition(org.hawkular.alerts.api.model.condition.CompareCondition.Operator.GTE, Match.ANY);
    }

    @Test(priority = 3)
    public void testCompareConditionLT() {
        testCompareCondition(org.hawkular.alerts.api.model.condition.CompareCondition.Operator.LT, Match.ANY);
    }

    @Test(priority = 3)
    public void testCompareConditionLTE() {
        testCompareCondition(org.hawkular.alerts.api.model.condition.CompareCondition.Operator.LTE, Match.ANY);
    }

    @Test(priority = 4)
    public void testStringConditionCONTAINS() {
        testStringCondition(org.hawkular.alerts.api.model.condition.StringCondition.Operator.CONTAINS, Match.ANY);
    }

    @Test(priority = 4)
    public void testStringConditionENDS_WITH() {
        testStringCondition(org.hawkular.alerts.api.model.condition.StringCondition.Operator.ENDS_WITH, Match.ANY);
    }

    @Test(priority = 4)
    public void testStringConditionEQUAL() {
        testStringCondition(org.hawkular.alerts.api.model.condition.StringCondition.Operator.EQUAL, Match.ANY);
    }

    @Test(priority = 4)
    public void testStringConditionMATCH() {
        testStringCondition(org.hawkular.alerts.api.model.condition.StringCondition.Operator.MATCH, Match.ANY);
    }

    @Test(priority = 4)
    public void testStringConditionNOT_EQUAL() {
        testStringCondition(org.hawkular.alerts.api.model.condition.StringCondition.Operator.NOT_EQUAL, Match.ANY);
    }

    @Test(priority = 4)
    public void testStringConditionSTARTS_WITH() {
        testStringCondition(org.hawkular.alerts.api.model.condition.StringCondition.Operator.STARTS_WITH, Match.ANY);
    }

    public void testStringCondition(org.hawkular.alerts.api.model.condition.StringCondition.Operator operator,
            Match match) {
        _logger.debug("Testing condition:{}", operator.toString());
        String dataId = "metric-data-id-" + getRandomId(); //MetricId also called dataId
        String triggerId = "trigger-id-string-condition-" + operator.toString() + "-" + getRandomId();

        double valueMin = getRandomDouble(doubleMinValue, 1000.0);
        double valueMax = getRandomDouble(valueMin, doubleMaxValue);

        //This is the reference, number of char in word
        int countMin = 3;
        int countMax = 20;

        //This is the reference, number of words in each data
        int wordsMin = 10;
        int wordsMax = 100;

        _logger.debug("Selected Values[Min:{}, Max:{}]", valueMin, valueMax);

        Trigger trigger = new Trigger(triggerId, "String-condition-" + operator.toString() + "-" + getRandomId());
        trigger.setFiringMatch(match);

        //Create Trigger
        createTrigger(trigger);

        //Setup new conditions
        //Matching pattern
        String matchingPattern = getRandomAlphanumericString(getRandomInteger(countMin, countMax));
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new StringCondition(triggerId, Mode.FIRING, dataId, operator,
                matchingPattern, getRandomBoolean()));

        //Add Conditions in to trigger
        addTriggerCondition(trigger, conditions, Mode.FIRING);

        //Enable Trigger
        trigger.setEnabled(true);

        //Update Trigger
        updateTrigger(trigger.getId(), trigger);

        //Prepare data
        RandomString randomString = new RandomString(TENANT.getId(), dataId, countMin, countMax,
                getRandomInteger(dataCountMin, dataCountMax), getRandomInteger(wordsMin, wordsMax), matchingPattern,
                delayTime);
        List<Data> numericDataList = getStringData(randomString);

        validateAndDelete(trigger, conditions, numericDataList, Match.ANY);

    }

    public void testCompareCondition(org.hawkular.alerts.api.model.condition.CompareCondition.Operator operator,
            Match match) {
        _logger.debug("Testing condition:{}", operator.toString());
        String dataId = "metric-data-id-" + getRandomId(); //MetricId also called dataId
        String dataId2 = "metric-data-id-" + getRandomId(); //MetricId also called dataId
        String triggerId = "trigger-id-compare-condition-" + operator.toString() + "-" + getRandomId();

        double data2Multiplier = getRandomDouble(0.0, 1.0); //load data2multiplier random
        double valueMin = getRandomDouble(doubleMinValue, 1000.0);
        double valueMax = getRandomDouble(valueMin, doubleMaxValue);

        _logger.debug("Selected Values[Min:{}, Max:{}]", valueMin, valueMax);

        Trigger trigger = new Trigger(triggerId, "CompareCondition-" + operator.toString() + "-" + getRandomId());
        trigger.setFiringMatch(match);

        //Create Trigger
        createTrigger(trigger);

        //Setup new conditions
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new CompareCondition(triggerId, Mode.FIRING, dataId, operator, data2Multiplier, dataId2));

        //Add Conditions in to trigger
        addTriggerCondition(trigger, conditions, Mode.FIRING);

        //Enable Trigger
        trigger.setEnabled(true);

        //Update Trigger
        updateTrigger(trigger.getId(), trigger);

        //Prepare data
        RandomDouble randomDouble = new RandomDouble(TENANT.getId(), dataId, dataId2, valueMin, valueMax,
                getRandomInteger(dataCountMin, dataCountMax), delayTime);
        List<Data> numericDataList = getNumericData(randomDouble);

        validateAndDelete(trigger, conditions, numericDataList, Match.ANY);

    }

    public void testThresholdCondition(Operator operator, Match match) {
        _logger.debug("Testing condition:{}", operator.toString());
        String dataId = "metric-data-id-" + getRandomId(); //MetricId also called dataId
        String triggerId = "trigger-id-threshold-" + operator.toString() + "-" + getRandomId();

        double valueMin = getRandomDouble(doubleMinValue, 1000.0);
        double valueMax = getRandomDouble(valueMin, doubleMaxValue);

        _logger.debug("Selected Values[Min:{}, Max:{}]", valueMin, valueMax);

        Trigger trigger = new Trigger(triggerId, "Threshold-" + operator.toString() + "-" + getRandomId());
        trigger.setFiringMatch(match);

        //Create Trigger
        createTrigger(trigger);

        //Setup new conditions
        List<Condition> conditions = new ArrayList<>();
        conditions.add(
                new ThresholdCondition(triggerId, Mode.FIRING, dataId, operator,
                        getRandomDouble(valueMin, valueMax)));

        //Add Conditions in to trigger
        addTriggerCondition(trigger, conditions, Mode.FIRING);

        //Enable Trigger
        trigger.setEnabled(true);

        //Update Trigger
        updateTrigger(trigger.getId(), trigger);

        //Prepare data
        RandomDouble randomDouble = new RandomDouble(TENANT.getId(), dataId, valueMin, valueMax,
                getRandomInteger(dataCountMin, dataCountMax), delayTime);
        List<Data> numericDataList = getNumericData(randomDouble);

        validateAndDelete(trigger, conditions, numericDataList, Match.ANY);

    }

    public void testThresholdRangeCondition(
            org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator operatorLow,
            org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator operatorHigh,
            boolean inRange,
            Match match) {

        _logger.debug("Testing condition:[OperatorLow:{}, OperatorHigh:{}, InRange:{}]",
                operatorLow.toString(), operatorHigh, inRange);

        String dataId = "metric-data-id-" + getRandomId(); //MetricId also called dataId
        String triggerId = "trigger-id-threshold-range-L_" + operatorLow.toString() + "-H_" + operatorHigh.toString()
                + "-R_" + inRange + "-" + getRandomId();

        double rangeMin = getRandomDouble(doubleMinValue, 10000.0);
        double rangeMax = getRandomDouble(rangeMin, doubleMaxValue);

        _logger.debug("Selected Values[RangeMin:{}, RangeMax:{}]", rangeMin, rangeMax);

        Trigger trigger = new Trigger(triggerId, "ThresholdRange-L_" + operatorLow.toString() + "-H_"
                + operatorHigh.toString() + "-R_" + inRange + "-" + getRandomId());
        trigger.setFiringMatch(match);

        //Create Trigger
        createTrigger(trigger);

        //Setup new conditions
        List<Condition> conditions = new ArrayList<>();
        conditions.add(
                new ThresholdRangeCondition(
                        triggerId,
                        Mode.FIRING,
                        dataId,
                        operatorLow, operatorHigh,
                        rangeMin, rangeMax,
                        inRange));

        //Add Conditions in to trigger
        addTriggerCondition(trigger, conditions, Mode.FIRING);

        //Enable Trigger
        trigger.setEnabled(true);

        //Update Trigger
        updateTrigger(trigger.getId(), trigger);

        //Prepare data
        RandomDouble randomDouble = new RandomDouble(TENANT.getId(), dataId, doubleMinValue, doubleMaxValue,
                getRandomInteger(dataCountMin, dataCountMax), delayTime);
        List<Data> numericDataList = getNumericData(randomDouble);

        validateAndDelete(trigger, conditions, numericDataList, Match.ANY);

    }

    public void testAvailabilityCondition(
            org.hawkular.alerts.api.model.condition.AvailabilityCondition.Operator operator,
            Match match) {

        _logger.debug("Testing condition:[Availability:{}]", operator.toString());

        String dataId = "metric-data-id-" + getRandomId(); //MetricId also called dataId
        String triggerId = "trigger-id-availability-" + operator.toString() + "-" + getRandomId();

        Trigger trigger = new Trigger(triggerId, "Availability-" + operator.toString() + "-" + getRandomId());
        trigger.setFiringMatch(match);

        //Create Trigger
        createTrigger(trigger);

        //Setup new conditions
        List<Condition> conditions = new ArrayList<>();
        conditions.add(
                new AvailabilityCondition(triggerId, Mode.FIRING, dataId, operator));

        //Add Conditions in to trigger
        addTriggerCondition(trigger, conditions, Mode.FIRING);

        //Enable Trigger
        trigger.setEnabled(true);

        //Update Trigger
        updateTrigger(trigger.getId(), trigger);

        //Prepare data
        RandomAvailability randomAvailability = new RandomAvailability(TENANT.getId(), dataId, getRandomInteger(
                dataCountMin, dataCountMax), delayTime);
        List<Data> dataList = getAvailabilityData(randomAvailability);

        validateAndDelete(trigger, conditions, dataList, Match.ANY);

    }

    private void validateAndDelete(Trigger trigger, List<Condition> conditions, List<Data> datums, Match match) {
        //Validate locally
        ConditionsModel conditionsModel = new ConditionsModel(conditions, datums, match);
        validateConditions(conditionsModel);

        _logger.debug("Local validations Result:[{}]", conditionsModel.getTriggeredConditionsMap());

        //Add data
        sendData(datums);

        //Update Alerts Params
        AlertsParams alertsParams = new AlertsParams();
        alertsParams.setTriggerIds(trigger.getId());

        //Check is there any alert, wait and check for at least 10 seconds
        List<Alert> alerts = null;
        for (int count = 0; count < 20; count++) {
            alerts = getAlerts(alertsParams);
            if (alerts != null && !alerts.isEmpty()) {
                if (alerts.size() == conditionsModel.getTotalTriggeredCount()) {
                    break;
                }
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                _logger.error("Exception on thread sleep, ", ex);
            }
        }
        if (alerts == null) {
            alerts = new ArrayList<Alert>();
        }

        Assert.assertEquals(alerts.size(), conditionsModel.getTotalTriggeredCount(),
                "Trigger[" + trigger.getId() + "] count validation");

        if (alerts.size() > 0) {
            //Acknowledge alert
            String alertIds = getAlertIds(alerts);
            alertsParams.setAlertIds(alertIds);

            if (alertIds != null) {
                //Acknowledge alerts
                acknowledgeAlerts(alertIds, "rest-test-automation", "This alert acknowledged by REST test automation");
                //Delete alert
                int deleteAlertsCount = deleteAlerts(alertsParams);
                _logger.debug("Number of alerts deleted:{}", deleteAlertsCount);
                Assert.assertEquals(deleteAlertsCount, alerts.size());
            }
        }

        //Remove Conditions
        addTriggerCondition(trigger, conditions, Mode.FIRING);

        //Remove Trigger
        deleteTrigger(trigger);

    }
}

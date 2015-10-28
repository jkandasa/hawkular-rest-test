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
package org.hawkular.qe.rest.alerts;

import org.hawkular.alerts.api.model.condition.AvailabilityCondition;
import org.hawkular.alerts.api.model.condition.Condition;
import org.hawkular.alerts.api.model.condition.ThresholdCondition;
import org.hawkular.alerts.api.model.condition.ThresholdRangeCondition;
import org.hawkular.alerts.api.model.condition.ThresholdRangeCondition.Operator;
import org.hawkular.alerts.api.model.data.Availability;
import org.hawkular.alerts.api.model.data.Availability.AvailabilityType;
import org.hawkular.alerts.api.model.data.NumericData;
import org.hawkular.qe.rest.alerts.model.ConditionsModel;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class ValidateConditions extends AlertsTestBase {

    public void validateConditions(ConditionsModel conditionsModel) {

        for (Condition condition : conditionsModel.getConditions()) {
            switch (condition.getType()) {
                case THRESHOLD:
                    validateThresholdCondition((ThresholdCondition) condition, conditionsModel);
                    break;
                case RANGE:
                    validateThresholdRangeCondition((ThresholdRangeCondition) condition, conditionsModel);
                    break;
                case COMPARE:
                    break;
                case AVAILABILITY:
                    validateAvailabilityCondition((AvailabilityCondition) condition, conditionsModel);
                    break;
                case STRING:
                    break;
                case EXTERNAL:
                    break;
                default:
                    break;
            }
        }
    }

    public void validateThresholdCondition(ThresholdCondition condition, ConditionsModel conditionsModel) {
        for (NumericData data : conditionsModel.getMixedData().getNumericData()) {
            switch (condition.getOperator()) {
                case GT:
                    if (data.getValue() > condition.getThreshold()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }
                    break;
                case GTE:
                    if (data.getValue() >= condition.getThreshold()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }
                    break;
                case LT:
                    if (data.getValue() < condition.getThreshold()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }
                    break;
                case LTE:
                    if (data.getValue() <= condition.getThreshold()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void validateThresholdRangeCondition(ThresholdRangeCondition condition, ConditionsModel conditionsModel) {
        for (NumericData data : conditionsModel.getMixedData().getNumericData()) {
            if (condition.getOperatorLow() == Operator.INCLUSIVE
                    && condition.getOperatorHigh() == Operator.INCLUSIVE) {
                if (data.getValue() >= condition.getThresholdLow()
                        && data.getValue() <= condition.getThresholdHigh()) {
                    if (condition.isInRange()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }

                } else if (!condition.isInRange()) {
                    conditionsModel.increaseTriggeredConditionCount(condition);
                }
            } else if (condition.getOperatorLow() == Operator.INCLUSIVE
                    && condition.getOperatorHigh() == Operator.EXCLUSIVE) {
                if (data.getValue() >= condition.getThresholdLow()
                        && data.getValue() < condition.getThresholdHigh()) {
                    if (condition.isInRange()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }

                } else if (!condition.isInRange()) {
                    conditionsModel.increaseTriggeredConditionCount(condition);
                }
            } else if (condition.getOperatorLow() == Operator.EXCLUSIVE
                    && condition.getOperatorHigh() == Operator.EXCLUSIVE) {
                if (data.getValue() > condition.getThresholdLow()
                        && data.getValue() < condition.getThresholdHigh()) {
                    if (condition.isInRange()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }

                } else if (!condition.isInRange()) {
                    conditionsModel.increaseTriggeredConditionCount(condition);
                }
            } else if (condition.getOperatorLow() == Operator.EXCLUSIVE
                    && condition.getOperatorHigh() == Operator.INCLUSIVE) {
                if (data.getValue() > condition.getThresholdLow()
                        && data.getValue() <= condition.getThresholdHigh()) {
                    if (condition.isInRange()) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }

                } else if (!condition.isInRange()) {
                    conditionsModel.increaseTriggeredConditionCount(condition);
                }
            }
        }
    }

    public void validateAvailabilityCondition(AvailabilityCondition condition, ConditionsModel conditionsModel) {
        for (Availability data : conditionsModel.getMixedData().getAvailability()) {
            switch (condition.getOperator()) {
                case UP:
                    if (data.getValue() == AvailabilityType.UP) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }
                    break;
                case DOWN:
                    if (data.getValue() == AvailabilityType.DOWN) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }
                    break;
                case NOT_UP:
                    if (data.getValue() != AvailabilityType.UP) {
                        conditionsModel.increaseTriggeredConditionCount(condition);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}

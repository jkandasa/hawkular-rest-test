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
package org.hawkular.qe.rest.test.metrics.unittest;

import java.util.ArrayList;
import java.util.List;

import org.hawkular.client.metrics.model.TenantParam;
import org.hawkular.metrics.core.api.Tenant;
import org.hawkular.qe.rest.base.metrics.MetricsTestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author jkandasa@redhat.com (Jeeva Kandasamy)
 */
public class TenantTest extends MetricsTestBase {

    @Test(dataProvider = "tenantDataProvider", priority = 1)
    public void createsTest(TenantParam tenant) {
        if (isTenantAvailable(getHawkularClient().metrics().getTenants(), tenant)) {
            _logger.debug("A tenant with id [{}] already exists", tenant.getId());
        } else {
            Assert.assertTrue(getHawkularClient().metrics().createTenant(new Tenant(tenant.getId())));
        }
    }

    @SuppressWarnings("unchecked")
    @Test(priority = 2)
    public void findTest() {
        List<TenantParam> tenantsRx = getHawkularClient().metrics().getTenants();
        assertTenantsList(tenantsRx, (List<TenantParam>) getTenants());
    }

    @SuppressWarnings("unchecked")
    @DataProvider(name = "tenantDataProvider")
    public Object[][] resourceDataProvider() {
        return this.get2dArray((List<Object>) getTenants());
    }

    public static List<? extends Object> getTenants() {
        List<TenantParam> tenants = new ArrayList<>();
        tenants.add(getTenant("tenant1"));
        tenants.add(getTenant("_t2"));
        tenants.add(getTenant("3t_"));
        tenants.add(getTenant("t-4"));
        tenants.add(getTenant("tenantwithlooooooooooooooooooooooooooooooooongstring"));
        tenants.add(getTenant("tenantwith.dot"));
        return tenants;
    }

    private static TenantParam getTenant(String id) {
        TenantParam tenant = new TenantParam(new Tenant(id));
        return tenant;
    }

}

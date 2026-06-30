package com.gentics.mesh.core.endpoint.admin.debuginfo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

import io.vertx.ext.web.RoutingContext;

public class IncludedInfoTest {

	@Test
	public void shouldSplitIncludedAndExcludedQueryParts() {
		RoutingContext routingContext = mock(RoutingContext.class);
		when(routingContext.queryParam("include")).thenReturn(Collections.singletonList("config,-logs,+metrics,  ,-database"));

		IncludedInfo includedInfo = new IncludedInfo(routingContext);

		assertEquals(new HashSet<>(Arrays.asList("config", "metrics")), includedInfo.getIncluded());
		assertEquals(new HashSet<>(Arrays.asList("logs", "database")), includedInfo.getExcluded());
	}

	@Test
	public void shouldTreatMissingIncludeParameterAsEmptySelection() {
		RoutingContext routingContext = mock(RoutingContext.class);
		when(routingContext.queryParam("include")).thenReturn(Collections.emptyList());

		IncludedInfo includedInfo = new IncludedInfo(routingContext);

		assertEquals(Collections.emptySet(), includedInfo.getIncluded());
		assertEquals(Collections.emptySet(), includedInfo.getExcluded());
	}
}

/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.search.aggregations.matrix.stats;

import org.elasticsearch.search.MultiValueMode;
import org.elasticsearch.search.aggregations.Aggregator;
import org.elasticsearch.search.aggregations.AggregatorFactories;
import org.elasticsearch.search.aggregations.AggregatorFactory;
import org.elasticsearch.search.aggregations.pipeline.PipelineAggregator;
import org.elasticsearch.search.aggregations.support.MultiValuesSourceAggregatorFactory;
import org.elasticsearch.search.aggregations.support.ValuesSource;
import org.elasticsearch.search.aggregations.support.ValuesSourceConfig;
import org.elasticsearch.search.internal.SearchContext;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MatrixStatsAggregatorFactory
    extends MultiValuesSourceAggregatorFactory<ValuesSource.Numeric, MatrixStatsAggregatorFactory> {

    private final MultiValueMode multiValueMode;

    public MatrixStatsAggregatorFactory(String name,
            Map<String, ValuesSourceConfig<ValuesSource.Numeric>> configs, MultiValueMode multiValueMode,
            SearchContext context, AggregatorFactory<?> parent, AggregatorFactories.Builder subFactoriesBuilder,
            Map<String, Object> metaData) throws IOException {
        super(name, configs, context, parent, subFactoriesBuilder, metaData);
        this.multiValueMode = multiValueMode;
    }

    @Override
    protected Aggregator createUnmapped(Aggregator parent, List<PipelineAggregator> pipelineAggregators, Map<String, Object> metaData)
        throws IOException {
        return new MatrixStatsAggregator(name, null, context, parent, multiValueMode, pipelineAggregators, metaData);
    }

    @Override
    protected Aggregator doCreateInternal(Map<String, ValuesSource.Numeric> valuesSources, Aggregator parent,
                                          boolean collectsFromSingleBucket, List<PipelineAggregator> pipelineAggregators,
                                          Map<String, Object> metaData) throws IOException {
        return new MatrixStatsAggregator(name, valuesSources, context, parent, multiValueMode, pipelineAggregators, metaData);
    }
}

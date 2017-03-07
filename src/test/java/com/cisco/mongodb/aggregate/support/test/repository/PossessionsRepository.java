/*
 *  Copyright (c) 2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *
 */

package com.cisco.mongodb.aggregate.support.test.repository;

import com.cisco.mongodb.aggregate.support.annotation.Aggregate;
import com.cisco.mongodb.aggregate.support.annotation.Match;
import com.cisco.mongodb.aggregate.support.test.beans.Possessions;
import com.mongodb.DBObject;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by rkolliva
 * 3/1/17.
 */
public interface PossessionsRepository extends MongoRepository<Possessions, String> {

  @Aggregate(inputType = Possessions.class, outputBeanType = Possessions.class,
             match = {
                 @Match(query = "{" +
                                "   \"assets.@0\": { $exists: true, $ne : []}" +
                                "   }" +
                                "}", order = 0)
             })
  List<Possessions> getPossessions(String type);

  default boolean hasCars() {
    return CollectionUtils.isNotEmpty(getPossessions( "cars"));
  }

  default boolean hasHomes(String id) {
    return CollectionUtils.isNotEmpty(getPossessions( "homes"));
  }

  @Aggregate(inputType = Possessions.class, outputBeanType = DBObject.class,
             match = {
                 @Match(query = "{" +
                                "   \"_id\": ?0" +
                                "}", order = 0)
             })
  List<DBObject> getPossessionsDbObject(String id);

}
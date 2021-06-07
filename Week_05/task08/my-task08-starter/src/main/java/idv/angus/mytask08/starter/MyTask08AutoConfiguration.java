/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package idv.angus.mytask08.starter;

import idv.angus.mytask08.starter.mission.ISchool;
import idv.angus.mytask08.starter.mission.Klass;
import idv.angus.mytask08.starter.mission.School;
import idv.angus.mytask08.starter.mission.Student;
import idv.angus.mytask08.starter.prop.KlassProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@EnableConfigurationProperties(KlassProperties.class)
@RequiredArgsConstructor
public class MyTask08AutoConfiguration {
    @Autowired
    KlassProperties properties;

    @Bean
    public Klass createKlass(KlassProperties properties) {
        return new Klass(Optional.ofNullable(properties.getStudents())
                .orElseGet(ArrayList::new));
    }

    @Bean
    public ISchool createSchool() {
        return new School();
    }

    @Bean(name = "student100")
    public Student student() {
        System.out.println("create a outside student");
        return new Student(Math.abs(ThreadLocalRandom.current().nextInt()),
                "student 100");
    }
}

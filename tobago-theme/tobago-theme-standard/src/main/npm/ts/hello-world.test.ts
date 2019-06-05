/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {es6test, hello, T} from "./hello-world";
import HelloWorld = T.HelloWorld;

test('Hello function', () => {
  const result = hello();
  expect(result).toBe('Hello world!');
});

test('constructor', () => {
  const result = new HelloWorld("Hello world!");
  expect(result.value).toBe('Hello world!');
});

test('Adding', () => {
  expect(es6test()).toStrictEqual([0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100, 121, 144, 169, 196, 225, 256]);
});

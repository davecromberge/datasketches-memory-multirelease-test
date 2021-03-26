/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.datasketches.memory.multirelease.test;

import org.apache.datasketches.memory.Memory;
import org.apache.datasketches.memory.WritableBuffer;
import org.apache.datasketches.memory.WritableDirectHandle;
import org.apache.datasketches.memory.WritableMemory;

import org.example.mrjar.C;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

class Main {

    public static void MrJARTest() {
        C c = new C();
        System.out.println(c.getName());
        // System.out.println(c.getLegacyName());
        // System.out.println(c.getNewName());
    }

    public static void OnHeapArrayTest() {
        byte[] arr = new byte[16];
        WritableMemory wmem = WritableMemory.wrap(arr);
        wmem.putByte(1, (byte) 1);
        int v = wmem.getInt(0);
        assert ( v == 256 );

        //Or by wrapping an existing primitive array:
        byte[] array = new byte[] {1, 0, 0, 0, 2, 0, 0, 0};
        Memory mem = Memory.wrap(array);
        assert mem.getInt(0) == 1;
        assert mem.getInt(4) == 2;

        arr[9] = 3; //you can also access the backing array directly
        long v2 = mem.getLong(8);
        assert ( v2 == 768L);
        System.out.println("Passed on-heap array test");
    }

    public static void SimpleBBTest() {
        int n = 1024; //longs
        byte[] arr = new byte[n * 8];
        ByteBuffer bb = ByteBuffer.wrap(arr);
        bb.order(ByteOrder.nativeOrder());

        WritableBuffer wbuf = WritableBuffer.wrap(bb);
        for (int i = 0; i < n; i++) { //write to wbuf
            wbuf.putLong(i);
        }
        wbuf.resetPosition();
        for (int i = 0; i < n; i++) { //read from wbuf
            long v = wbuf.getLong();
            assert(v == i);
        }
        for (int i = 0; i < n; i++) { //read from BB
            long v = bb.getLong();
            assert(v == i);
        }
        System.out.println("Passed simple BB test");
    }

    public static void SimpleAllocateDirect() {
        int longs = 32;
        try (WritableDirectHandle wh = WritableMemory.allocateDirect(longs << 3)) {
            WritableMemory wMem1 = wh.get();
            for (int i = 0; i<longs; i++) {
                wMem1.putLong(i << 3, i);
                assert(wMem1.getLong(i << 3) == i);
            }
        }
        System.out.println("Passed simple allocate direct");
    }

    public static void main(String[] args) {
        MrJARTest();
        OnHeapArrayTest();
        SimpleBBTest();
        SimpleAllocateDirect();
    }
}
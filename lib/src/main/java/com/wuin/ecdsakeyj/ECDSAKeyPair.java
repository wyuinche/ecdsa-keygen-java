/* Copyright 2022 wyuinche
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.wuin.ecdsakeyj;

public abstract class ECDSAKeyPair {
    protected String priv;
    protected String pub;

    protected ECDSAKeyPair() {
        this.priv = "";
        this.pub = "";
    }

    protected ECDSAKeyPair(String priv) {
        this.priv = priv;
    }

    public String getPrivateKey() {
        return this.priv;
    }

    public String getPublicKey() {
        return this.pub;
    }

    public abstract void createPublicKey();
    public abstract byte[] sign(String msg);
    public abstract byte[] sign(byte[] target);
}
     
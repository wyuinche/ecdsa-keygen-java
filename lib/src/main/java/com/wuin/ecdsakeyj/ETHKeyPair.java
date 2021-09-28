/* Copyright 2021 wyuinche
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

import org.bitcoinj.core.*;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.ECDSASignature;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ETHKeyPair extends ECDSAKeyPair {
    private ECKeyPair keypair;

    public ETHKeyPair() {
        String _priv = createPrivateKey();
        if(_priv != null) {
            this.priv = _priv;
        }
        createPublicKey();
    }    

    public ETHKeyPair(String priv) {
        super(priv);
        createPublicKey();
    }

    private String createPrivateKey() {
        try {
            return new ECKey().getPrivateKeyAsHex();
        } catch(Exception e) {
            Util.raiseError("Fail to generate new ETHER Keypair.");
            return null;
        }
    }

    @Override
    public void createPublicKey() {
        this.keypair = ECKeyPair.create(Util.hexStringToBytes(this.priv));
        
        String _pub = Util.bytesToHexString(this.keypair.getPublicKey().toByteArray());
        this.pub = "04" + _pub.substring(0, _pub.length()).toLowerCase();
    }

    @Override
    public byte[] sign(String msg) {
        return sign(msg.getBytes());
    }

    @Override
    public byte[] sign(byte[] target){
        ECDSASignature _sig = this.keypair.sign(Sha256Hash.hash(target)).toCanonicalised();
        byte[] r = _sig.r.toByteArray();
        byte[] s = _sig.s.toByteArray();

        int rlen = r.length;
        int slen = s.length;

        byte[] brlen = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(rlen).array();

        byte[] sig = new byte[rlen + slen + 4];
        System.arraycopy(brlen, 0, sig, 0, 4);
        System.arraycopy(r, 0, sig, 4, rlen);
        System.arraycopy(s, 0, sig, 4 + rlen, slen);

        return sig;
    }
}

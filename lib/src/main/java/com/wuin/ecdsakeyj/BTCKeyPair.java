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

import java.math.BigInteger;
import java.util.Arrays;

import org.bitcoinj.core.*;
import org.bitcoinj.params.*;

public class BTCKeyPair extends ECDSAKeyPair {
    private ECKey keypair;

    public BTCKeyPair() {
        String _priv = createPrivateKey();
        if (_priv != null) {
            this.priv = _priv;
        }
        createPublicKey();
    }

    public BTCKeyPair(String priv, Boolean useSeed) {
        if (useSeed) {
            byte[] sh = Util.sha3(priv);
            byte[] shb = Base58.encode(sh).getBytes();
            shb = Arrays.copyOfRange(shb, 0, shb.length - 4);

            BigInteger k = new BigInteger(shb);
            BigInteger n = new BigInteger("115792089237316195423570985008687907852837564279074904382605163141518161494336");

            k = k.mod(n);
            k = k.add(new BigInteger("1"));
            String key = Util.bytesToHexString(k.toByteArray());
            
            try {
                String _priv = encodeKey(key);
                this.priv = _priv;
            } catch (Exception e) {
                Util.raiseError("Fail to generate BTC Keypair from seed.");
            }
        } else {
            this.priv = priv;
        }
        createPublicKey();
    }

    private String createPrivateKey() {
        try {
            ECKey keypair = new ECKey();
            return encodeKey(keypair.getPrivateKeyAsHex());
        } catch (Exception e) {
            Util.raiseError("Fail to generate new BTC Keypair.");
            return null;
        }
    }

    private String encodeKey(String key) {
        try {
            String _priv = "80" + key + "01";
            byte[] _bpriv = Util.hexStringToBytes(_priv);

            byte[] _hs = Sha256Hash.hashTwice(_bpriv);

            byte[] checksum = new byte[4];
            System.arraycopy(_hs, 0, checksum, 0, 4);

            byte[] _pk = new byte[_bpriv.length + 4];
            System.arraycopy(_bpriv, 0, _pk, 0, _bpriv.length);
            System.arraycopy(checksum, 0, _pk, _bpriv.length, 4);

            return Base58.encode(_pk);
        } catch (Exception e) {
            Util.raiseError("Fail to generate new BTC Keypair.");
            return null;
        }
    }

    @Override
    public void createPublicKey() {
        NetworkParameters params = MainNetParams.get();

        DumpedPrivateKey dumpedPrivateKey = DumpedPrivateKey.fromBase58(params, this.priv);
        this.keypair = dumpedPrivateKey.getKey();

        String _pub = this.keypair.getPublicKeyAsHex();
        byte[] _bpub = Util.hexStringToBytes(_pub);

        this.pub = Base58.encode(_bpub);
    }

    @Override
    public byte[] sign(String msg) {
        return sign(msg.getBytes());
    }

    @Override
    public byte[] sign(byte[] target) {
        return keypair.sign(Sha256Hash.twiceOf(target)).encodeToDER();
    }
}

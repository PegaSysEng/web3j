/*
 * Copyright 2019 Web3 Labs LTD.
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
package org.web3j.protocol.besu;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.web3j.protocol.Web3jService;
import org.web3j.protocol.admin.methods.response.BooleanResponse;
import org.web3j.protocol.besu.crypto.crosschain.BlsThresholdCryptoSystem;
import org.web3j.protocol.besu.response.BesuEthAccountsMapResponse;
import org.web3j.protocol.besu.response.BesuFullDebugTraceResponse;
import org.web3j.protocol.besu.response.crosschain.CrossBlockchainPublicKeyResponse;
import org.web3j.protocol.besu.response.crosschain.CrossCheckUnlockResponse;
import org.web3j.protocol.besu.response.crosschain.CrossIsLockableResponse;
import org.web3j.protocol.besu.response.crosschain.CrossIsLockedResponse;
import org.web3j.protocol.besu.response.crosschain.CrossProcessSubordinateViewResponse;
import org.web3j.protocol.besu.response.crosschain.KeyGenFailureReasonResponse;
import org.web3j.protocol.besu.response.crosschain.KeyGenNodesDroppedOutOfKeyGenerationResponse;
import org.web3j.protocol.besu.response.crosschain.KeyStatusResponse;
import org.web3j.protocol.besu.response.crosschain.ListBlockchainNodesResponse;
import org.web3j.protocol.besu.response.crosschain.ListCoordinationContractsResponse;
import org.web3j.protocol.besu.response.crosschain.ListNodesResponse;
import org.web3j.protocol.besu.response.crosschain.LongResponse;
import org.web3j.protocol.besu.response.crosschain.NoResponse;
import org.web3j.protocol.besu.response.privacy.PrivCreatePrivacyGroup;
import org.web3j.protocol.besu.response.privacy.PrivFindPrivacyGroup;
import org.web3j.protocol.besu.response.privacy.PrivGetPrivacyPrecompileAddress;
import org.web3j.protocol.besu.response.privacy.PrivGetPrivateTransaction;
import org.web3j.protocol.besu.response.privacy.PrivGetTransactionReceipt;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.MinerStartResponse;
import org.web3j.protocol.eea.Eea;
import org.web3j.utils.Base64String;

public interface Besu extends Eea {
    static Besu build(Web3jService web3jService) {
        return new JsonRpc2_0Besu(web3jService);
    }

    static Besu build(Web3jService web3jService, long pollingInterval) {
        return new JsonRpc2_0Besu(web3jService, pollingInterval);
    }

    Request<?, MinerStartResponse> minerStart();

    Request<?, BooleanResponse> minerStop();

    /** @deprecated This is deprecated as the method name is wrong. */
    default Request<?, BooleanResponse> clicqueDiscard(String address) {
        return cliqueDiscard(address);
    }

    /** @deprecated This is deprecated as the method name is wrong. */
    default Request<?, EthAccounts> clicqueGetSigners(DefaultBlockParameter defaultBlockParameter) {
        return cliqueGetSigners(defaultBlockParameter);
    }

    /** @deprecated This is deprecated as the method name is wrong. */
    default Request<?, EthAccounts> clicqueGetSignersAtHash(String blockHash) {
        return cliqueGetSignersAtHash(blockHash);
    }

    Request<?, BooleanResponse> cliqueDiscard(String address);

    Request<?, EthAccounts> cliqueGetSigners(DefaultBlockParameter defaultBlockParameter);

    Request<?, EthAccounts> cliqueGetSignersAtHash(String blockHash);

    Request<?, BooleanResponse> cliquePropose(String address, Boolean signerAddition);

    Request<?, BesuEthAccountsMapResponse> cliqueProposals();

    Request<?, BesuFullDebugTraceResponse> debugTraceTransaction(
            String transactionHash, Map<String, Boolean> options);

    Request<?, EthGetTransactionCount> privGetTransactionCount(
            final String address, final Base64String privacyGroupId);

    Request<?, PrivGetPrivateTransaction> privGetPrivateTransaction(final String transactionHash);

    Request<?, PrivGetPrivacyPrecompileAddress> privGetPrivacyPrecompileAddress();

    Request<?, PrivCreatePrivacyGroup> privCreatePrivacyGroup(
            final List<Base64String> addresses, final String name, final String description);

    Request<?, PrivFindPrivacyGroup> privFindPrivacyGroup(final List<Base64String> addresses);

    Request<?, BooleanResponse> privDeletePrivacyGroup(final Base64String privacyGroupId);

    Request<?, PrivGetTransactionReceipt> privGetTransactionReceipt(final String transactionHash);

    Request<?, NoResponse> crossActivateKey(final long keyVersion);

    Request<?, NoResponse> crossAddLinkedNode(
            final BigInteger blockchainId, final String ipAddressAndPort);

    Request<?, NoResponse> crossAddCoordinationContract(
            final BigInteger blockchainId, final String address, final String ipAddressAndPort);

    Request<?, CrossCheckUnlockResponse> crossCheckUnlock(String address);

    Request<?, LongResponse> crossGetActiveKeyVersion();

    Request<?, CrossBlockchainPublicKeyResponse> crossGetBlockchainPublicKey();

    Request<?, CrossBlockchainPublicKeyResponse> crossGetBlockchainPublicKey(final long keyVersion);

    Request<?, ListNodesResponse> crossGetKeyActiveNodes(final long keyVersion);

    Request<?, KeyGenFailureReasonResponse> crossGetKeyGenFailureReason(final long keyVersion);

    Request<?, KeyGenNodesDroppedOutOfKeyGenerationResponse>
            crossGetKeyGenNodesDroppedOutOfKeyGeneration(final long keyVersion);

    Request<?, KeyStatusResponse> crossGetKeyStatus(final long keyVersion);

    Request<?, CrossIsLockableResponse> crossIsLockable(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, CrossIsLockedResponse> crossIsLocked(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, ListCoordinationContractsResponse> crossListCoordinationContracts();

    Request<?, ListBlockchainNodesResponse> crossListLinkedNodes();

    Request<?, CrossProcessSubordinateViewResponse> crossProcessSubordinateView(
            String signedTransactionData);

    Request<?, NoResponse> crossRemoveCoordinationContract(
            final BigInteger blockchainId, final String address);

    Request<?, NoResponse> crossRemoveLinkedNode(final BigInteger blockchainId);

    Request<?, EthSendTransaction> crossSendCrossChainRawTransaction(String signedTransactionData);

    Request<?, NoResponse> crossSetKeyGenerationContractAddress(final String address);

    Request<?, LongResponse> crossStartThresholdKeyGeneration(
            final int threshold, final BlsThresholdCryptoSystem cryptoSystem);
}

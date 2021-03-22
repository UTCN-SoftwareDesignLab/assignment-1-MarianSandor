package repository.clientInfo;

import model.ClientInfo;

import java.util.List;

public interface ClientInfoRepository {

    void updateInfo(Long clientInfoId, ClientInfo newClientInfo);

    ClientInfo findClientInfoById(Long clientInfoId);

    Long addClientInfo(ClientInfo clientInfo);

    void deleteClientInfoById(Long clientInfoId);

    List<ClientInfo> findAll();

    void removeAll();
}

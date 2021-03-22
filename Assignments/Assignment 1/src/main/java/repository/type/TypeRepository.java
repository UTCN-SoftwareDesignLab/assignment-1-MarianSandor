package repository.type;

import model.Client;
import model.Type;

public interface TypeRepository {

    void addType(String type);

    Type findTypeById(Long typeId);

    Type findTypeByName(String name);

    Type findTypeForClient(Long clientId);
}

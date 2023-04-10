package emblock.mosti.adapter.blockchain;

public enum ContractType {
    PUBLIC("public_account", 'P'),
    COMMUNITY("community_account", 'C');

    String tableName;
    char type;

    ContractType(String tableName, char type) {
        this.tableName = tableName;
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public String getTableName() {
        return this.tableName;
    }

    public static ContractType getContractType(char type) {
        switch(type) {
            case 'C' : {
                return ContractType.COMMUNITY;
            }
            case 'P' : {
                return ContractType.PUBLIC;
            }
        };

        return null;
    }
}

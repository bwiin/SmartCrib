package com.example.wiinb.smartcrib;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "smartcrib-mobilehub-2105602681-biometrics")

public class BiometricsDO {
    private String _datatype;
    private String _timestamp;
    private float _data;
    private Boolean _flag;

    @DynamoDBHashKey(attributeName = "datatype")
    @DynamoDBIndexHashKey(attributeName = "datatype", globalSecondaryIndexName = "datatype-timestamp")
    public String getDatatype() {
        return _datatype;
    }

    public void setDatatype(final String _datatype) {
        this._datatype = _datatype;
    }
    @DynamoDBRangeKey(attributeName = "timestamp")
    @DynamoDBIndexRangeKey(attributeName = "timestamp", globalSecondaryIndexName = "datatype-timestamp")
    public String getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(final String _timestamp) {
        this._timestamp = _timestamp;
    }

    @DynamoDBAttribute(attributeName = "data")
    public float getData() {return _data;}

    public void setData(final float _data) {
        this._data = _data;
    }
    @DynamoDBAttribute(attributeName = "flag")
    public Boolean getFlag() {
        return _flag;
    }

    public void setFlag(final Boolean _flag) {
        this._flag = _flag;
    }

}

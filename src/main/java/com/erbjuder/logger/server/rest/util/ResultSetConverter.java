/* 
 * Copyright (C) 2014 erbjuder.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.erbjuder.logger.server.rest.util;

//import org.codehaus.jettison.json.JSONArray;
//import org.codehaus.jettison.json.JSONObject;
import com.erbjuder.logger.server.common.helper.TimeStampUtils;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Stefan Andersson
 */
public class ResultSetConverter {

    /*
     */
    public List<String> toStringList(ResultSet rs) throws Exception {

        List<String> list = new ArrayList<String>();
        try {

            // we will need the column names, this will save the table meta-data like column nmae.
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            //loop through the ResultSet
            while (rs.next()) {

                //figure out how many columns there are
                int numColumns = rsmd.getColumnCount();

                //each row in the ResultSet will be converted to a JSON Object
                StringBuilder builder = new StringBuilder();
                // loop through all the columns and place them into the JSON Object
                for (int i = 1; i < numColumns + 1; i++) {
                    String column_name = rsmd.getColumnName(i);

                    if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                        builder.append(rs.getArray(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                        builder.append(rs.getInt(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                        builder.append(rs.getBoolean(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                        builder.append(rs.getBlob(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                        builder.append(rs.getDouble(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                        builder.append(rs.getFloat(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                        builder.append(rs.getInt(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                        builder.append(rs.getNString(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
//                        temp = rs.getString(column_name); //saving column data to temp variable
//                        temp = ESAPI.encoder().canonicalize(temp); //decoding data to base state
//                        temp = ESAPI.encoder().encodeForHTML(temp); //encoding to be browser safe
//                        obj.put(column_name, temp); //putting data into JSON object
//                    
                        builder.append(rs.getNString(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                        builder.append(rs.getInt(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                        builder.append(rs.getInt(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                        builder.append(rs.getDate(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.TIME) {
                        builder.append(rs.getTime(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                        builder.append(rs.getTimestamp(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.NUMERIC) {
                        builder.append(rs.getBigDecimal(column_name));

                    } else {
                        builder.append(rs.getObject(column_name));

                    }
                }//end foreach
                list.add(builder.toString());
            }//end while
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list; //return String list

    }

    public JSONArray toJSONArray(List<ResultSet> rsList) throws Exception {
        JSONArray json = new JSONArray();
        for (ResultSet rs : rsList) {
            json = toJSONArray(rs, json);
        }
        return json;
    }

    /**
     * This will convert database records into a JSON Array Simply pass in a
     * ResultSet from a database connection and it loop return a JSON array.
     *
     * It important to check to make sure that all DataType that are being used
     * is properly encoding.
     *
     * varchar is currently the only dataType that is being encode by ESAPI
     *
     * @param rs - database ResultSet
     * @return - JSON array
     * @throws Exception
     */
    public JSONArray toJSONArray(ResultSet rs) throws Exception {
        return this.toJSONArray(rs, new JSONArray());

    }

    private JSONArray toJSONArray(ResultSet rs, JSONArray json) throws Exception {

        String temp = null;
        try {

            // we will need the column names, this will save the table meta-data like column nmae.
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            //loop through the ResultSet
            while (rs.next()) {

                //figure out how many columns there are
                int numColumns = rsmd.getColumnCount();

                //each row in the ResultSet will be converted to a JSON Object
                JSONObject obj = new JSONObject();

                // loop through all the columns and place them into the JSON Object
                for (int i = 1; i < numColumns + 1; i++) {
                    String column_name = rsmd.getColumnName(i);
                    if (rsmd.getColumnType(i) == java.sql.Types.ARRAY) {
                        obj.put(column_name, rs.getArray(column_name).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.BIGINT) {
                        obj.put(column_name, rs.getBigDecimal(column_name).toBigInteger().toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.BOOLEAN) {
                        obj.put(column_name, ((Boolean) rs.getBoolean(column_name)).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                        obj.put(column_name, rs.getBlob(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.DOUBLE) {
                        obj.put(column_name, ((Double) rs.getDouble(column_name)).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.FLOAT) {
                        obj.put(column_name, ((Float) rs.getFloat(column_name)).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.INTEGER) {
                        obj.put(column_name, ((Integer) rs.getInt(column_name)).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.NVARCHAR) {
                        obj.put(column_name, rs.getNString(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR) {
//                        temp = rs.getString(column_name); //saving column data to temp variable
//                        temp = ESAPI.encoder().canonicalize(temp); //decoding data to base state
//                        temp = ESAPI.encoder().encodeForHTML(temp); //encoding to be browser safe
//                        obj.put(column_name, temp); //putting data into JSON object
//                    
                        obj.put(column_name, rs.getNString(column_name));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.TINYINT) {
                        obj.put(column_name, ((Integer) rs.getInt(column_name)).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.SMALLINT) {
                        obj.put(column_name, ((Integer) rs.getInt(column_name)).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.DATE) {
                        obj.put(column_name, rs.getDate(column_name).toString());

                    } else if (rsmd.getColumnType(i) == java.sql.Types.TIME) {
                        obj.put(column_name, TimeStampUtils.dateTimeToString(rs.getTime(column_name)));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.TIMESTAMP) {
                        obj.put(column_name, TimeStampUtils.timeStampToString(rs.getTimestamp(column_name)));

                    } else if (rsmd.getColumnType(i) == java.sql.Types.NUMERIC) {
                        obj.put(column_name, rs.getBigDecimal(column_name).toString());

                    } else {
                        obj.put(column_name, rs.getObject(column_name));

                    }
                }//end foreach
                json.add(obj);
            }//end while
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json; //return JSON array
    }
}

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
package com.erbjuder.logger.server.rest.services.dao;

import com.erbjuder.logger.server.rest.helper.SQLPrepareStatementHelper;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Stefan Andersson
 */
public class LoggerSchema extends MysqlConnection {

    public ResultSet search_logMessageList(
            String fromDate,
            String toDate,
            String transactionReferenceId,
            Boolean viewError,
            List<String> applicationNames,
            List<String> flowNames,
            List<String> flowPointNames,
            List<String> freeTextSearchList,
            List<String> dataPartitionList) throws Exception {

        ResultSet rs = null;
        try (Connection conn = MysqlConnection()) {

            StringBuilder prepareStatement = new StringBuilder();
            prepareStatement.append("SELECT ");
            prepareStatement.append("ID, APPLICATIONNAME, EXPIREDDATE, FLOWNAME, FLOWPOINTNAME, ");
            prepareStatement.append("ISERROR, TRANSACTIONREFERENCEID, UTCLOCALTIMESTAMP, UTCSERVERTIMESTAMP ");
            prepareStatement.append("FROM ").append("LogMessage ").append("WHERE ");

            // Between date
            prepareStatement.append("UTCSERVERTIMESTAMP BETWEEN ").append(SQLPrepareStatementHelper.toSQLValue(fromDate)).append(" AND ").append(SQLPrepareStatementHelper.toSQLValue(toDate)).append(" ");

            // Transaction ref ID
            if (transactionReferenceId != null && !transactionReferenceId.isEmpty()) {
                prepareStatement.append("AND ");
                prepareStatement.append("TRANSACTIONREFERENCEID LIKE ").append(SQLPrepareStatementHelper.toSQLValue(transactionReferenceId)).append(" ");
            }

            // view error
            if (viewError != null) {
                prepareStatement.append("AND ");
                prepareStatement.append("ISERROR = ").append(SQLPrepareStatementHelper.toSQLValue(viewError.toString())).append(" ");
            }

            // application names
            if (applicationNames != null && !applicationNames.isEmpty()) {
                prepareStatement.append("AND ");
                prepareStatement.append("APPLICATIONNAME IN ").append(SQLPrepareStatementHelper.toSQLList(applicationNames)).append(" ");
            }

            // flow names
            if (flowNames != null && !flowNames.isEmpty()) {
                prepareStatement.append("AND ");
                prepareStatement.append("FLOWNAME IN ").append(SQLPrepareStatementHelper.toSQLList(flowNames)).append(" ");
            }

            // flow point names
            if (flowPointNames != null && !flowPointNames.isEmpty()) {
                prepareStatement.append("AND ");
                prepareStatement.append("FLOWPOINTNAME IN ").append(SQLPrepareStatementHelper.toSQLList(flowPointNames)).append(" ");
            }

            // Free text search
            if (freeTextSearchList != null && !freeTextSearchList.isEmpty()
                    && dataPartitionList != null && !dataPartitionList.isEmpty()) {

                prepareStatement.append("AND ( ");
                int size = dataPartitionList.size();
                for (int i = 0; i < size; i++) {

                    String logMessageDataPartition = dataPartitionList.get(i);
                    StringBuilder partitionBuilder = this.search_LogMessageIdsFromPartition(
                            fromDate,
                            toDate,
                            "ID",
                            logMessageDataPartition,
                            freeTextSearchList
                    );

                    prepareStatement.append("ID IN ").append(" ( ").append(partitionBuilder.toString()).append(" ) ");

                    if (i < size - 1) {
                        prepareStatement.append("OR ");
                    }

                }

                prepareStatement.append(") ");
            }

            
            // 
            // Order by
            prepareStatement.append("ORDER BY UTCSERVERTIMESTAMP DESC ");
            
            
            
            CallableStatement stmt = conn.prepareCall(prepareStatement.toString());
            rs = stmt.executeQuery();
            conn.close();

        } catch (SQLException sqlError) {
            sqlError.printStackTrace();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return rs;

        }
        return rs;

    }

    public List<ResultSet> fetch_LogMessageData(
            String logMessageId,
            List<String> dataPartitionList) {

        List<ResultSet> rsList = new ArrayList<>();
        try (Connection conn = MysqlConnection()) {

            for (String databasePartition : dataPartitionList) {

                StringBuilder prepareStatement = new StringBuilder();
                prepareStatement.append("SELECT ");
                prepareStatement.append("ID, CONTENT, LABEL, MIMETYPE, MODIFIED, ");
                prepareStatement.append("CONTENTSIZE, SEARCHABLE, UTCLOCALTIMESTAMP, UTCSERVERTIMESTAMP, LOGMESSAGE_ID ");
                prepareStatement.append("FROM ").append(databasePartition).append(" WHERE LOGMESSAGE_ID = ").append(logMessageId);

                ResultSet rs = null;
                CallableStatement stmt = conn.prepareCall(prepareStatement.toString());
                rs = stmt.executeQuery();

                if (rs != null) {
                    rsList.add(rs);
                }
            }

             
            
            conn.close();
        } catch (SQLException sqlError) {
            sqlError.printStackTrace();
            return rsList;
        } catch (Exception e) {
            e.printStackTrace();
            return rsList;

        }
        return rsList;
    }

    private StringBuilder search_LogMessageIdsFromPartition(
            String fromDate,
            String toDate,
            String logMessageId,
            String logMessageDataPartition,
            List<String> freeTextSearchList
    ) {

        StringBuilder prepareStatement = new StringBuilder();
        String partitionID = logMessageDataPartition + "_ID";

        prepareStatement.append("SELECT ");
        prepareStatement.append("DISTINCT ( ID ) as ").append(partitionID);
        prepareStatement.append("FROM ").append(SQLPrepareStatementHelper.toSQLValue(logMessageDataPartition)).append("WHERE ");

        // Between date
        prepareStatement.append("UTCSERVERTIMESTAMP BETWEEN ").append(SQLPrepareStatementHelper.toSQLValue(fromDate)).append(" AND ").append(SQLPrepareStatementHelper.toSQLValue(toDate)).append(" ");
        prepareStatement.append("AND ");
        prepareStatement.append(partitionID).append(" = ").append(logMessageId);

        int size = freeTextSearchList.size();
        if (size > 0) {
            prepareStatement.append("AND ( ");

            for (int i = 0; i < size; i++) {

                String freeText = SQLPrepareStatementHelper.toSQLStartsWithValue(freeTextSearchList.get(i));
                prepareStatement.append("LABEL ").append(freeText);
                prepareStatement.append("OR ");
                prepareStatement.append("CONTENT ").append(freeText);

                if (i < size - 1) {
                    prepareStatement.append("OR ");
                }
            }

            prepareStatement.append(") ");

        }

        return prepareStatement;

    }

    public ResultSet search_LogMessageDataPartition(
            String fromDate,
            String toDate,
            String logMessageId,
            String logMessageDataPartition,
            List<String> freeTextSearchList
    ) throws Exception {

        ResultSet rs = null;
        try (Connection conn = MysqlConnection()) {

            StringBuilder prepareStatement = new StringBuilder();
            prepareStatement.append("SELECT ");
            prepareStatement.append("ID, LABEL, CONTENT, MIMETYPE, CONTENTSIZE, MODIFIED");
            prepareStatement.append("SEARCHABLE, UTCLOCALTIMESTAMP, UTCSERVERTIMESTAMP, LOG_MESSAGE_ID ");
            prepareStatement.append("FROM ").append(SQLPrepareStatementHelper.toSQLValue(logMessageDataPartition)).append("WHERE ");

            // Between date
            prepareStatement.append("UTCSERVERTIMESTAMP BETWEEN ").append(SQLPrepareStatementHelper.toSQLValue(fromDate)).append(" AND ").append(SQLPrepareStatementHelper.toSQLValue(toDate)).append(" ");

            if (logMessageId != null && !logMessageId.isEmpty()) {
                prepareStatement.append("AND ");
                prepareStatement.append("ID = ").append(logMessageId);
            }

            int size = freeTextSearchList.size();
            if (size > 0) {
                prepareStatement.append("AND ( ");

                for (int i = 0; i < size; i++) {

                    String freeText = SQLPrepareStatementHelper.toSQLStartsWithValue(freeTextSearchList.get(i));
                    prepareStatement.append("LABEL ").append(freeText);
                    prepareStatement.append("OR ");
                    prepareStatement.append("CONTENT ").append(freeText);

                    if (i < size - 1) {
                        prepareStatement.append("OR ");
                    }
                }

                prepareStatement.append(") ");

            }

            CallableStatement stmt = conn.prepareCall(prepareStatement.toString());
            rs = stmt.executeQuery();
            conn.close();

        } catch (SQLException sqlError) {
            sqlError.printStackTrace();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return rs;

        }
        return rs;

    }

    private StringBuilder search_logMessagesFromApplicationNames(
            String fromDate,
            String toDate,
            List<String> applicationNames
    ) {

        StringBuilder prepareStatement = new StringBuilder();

        prepareStatement.append("SELECT ");
        prepareStatement.append("ID, APPLICATIONNAME, EXPIREDDATE, FLOWNAME, FLOWPOINTNAME, ");
        prepareStatement.append("ISERROR, TRANSACTIONREFERENCEID, UTCLOCALTIMESTAMP, UTCSERVERTIMESTAMP ");
        prepareStatement.append("FROM ").append("LogMessage ").append("WHERE ");

        // Between date
        prepareStatement.append("UTCSERVERTIMESTAMP BETWEEN ").append(SQLPrepareStatementHelper.toSQLValue(fromDate)).append(" AND ").append(SQLPrepareStatementHelper.toSQLValue(toDate)).append(" ");

        for (String applicationName : applicationNames) {
            prepareStatement.append("AND ");
            prepareStatement.append("APPLICATIONNAME LIKE ").append(SQLPrepareStatementHelper.toSQLStartsWithValue(applicationName)).append(" ");
        }

        return prepareStatement;

    }

    private StringBuilder search_logMessagesFromFlowNames(
            String fromDate,
            String toDate,
            List<String> flowNames
    ) {

        StringBuilder prepareStatement = new StringBuilder();

        prepareStatement.append("SELECT ");
        prepareStatement.append("ID, APPLICATIONNAME, EXPIREDDATE, FLOWNAME, FLOWPOINTNAME, ");
        prepareStatement.append("ISERROR, TRANSACTIONREFERENCEID, UTCLOCALTIMESTAMP, UTCSERVERTIMESTAMP ");
        prepareStatement.append("FROM ").append("LogMessage ").append("WHERE ");

        // Between date
        prepareStatement.append("UTCSERVERTIMESTAMP BETWEEN ").append(SQLPrepareStatementHelper.toSQLValue(fromDate)).append(" AND ").append(SQLPrepareStatementHelper.toSQLValue(toDate)).append(" ");

        for (String flowName : flowNames) {
            prepareStatement.append("AND ");
            prepareStatement.append("APPLICATIONNAME LIKE ").append(SQLPrepareStatementHelper.toSQLStartsWithValue(flowName)).append(" ");
        }

        return prepareStatement;

    }

    private StringBuilder search_logMessagesFromFlowPointNames(
            String fromDate,
            String toDate,
            List<String> flowPointNames
    ) {

        StringBuilder prepareStatement = new StringBuilder();

        prepareStatement.append("SELECT ");
        prepareStatement.append("ID, APPLICATIONNAME, EXPIREDDATE, FLOWNAME, FLOWPOINTNAME, ");
        prepareStatement.append("ISERROR, TRANSACTIONREFERENCEID, UTCLOCALTIMESTAMP, UTCSERVERTIMESTAMP ");
        prepareStatement.append("FROM ").append("LogMessage ").append("WHERE ");

        // Between date
        prepareStatement.append("UTCSERVERTIMESTAMP BETWEEN ").append(SQLPrepareStatementHelper.toSQLValue(fromDate)).append(" AND ").append(SQLPrepareStatementHelper.toSQLValue(toDate)).append(" ");

        for (String flowPointName : flowPointNames) {
            prepareStatement.append("AND ");
            prepareStatement.append("APPLICATIONNAME LIKE ").append(SQLPrepareStatementHelper.toSQLStartsWithValue(flowPointName)).append(" ");
        }

        return prepareStatement;

    }

    private StringBuilder search_PartitionContainsValue(
            String fromDate,
            String toDate,
            String freeText,
            String databasePartition
    ) {

        StringBuilder prepareStatement = new StringBuilder();

        prepareStatement.append("SELECT ");
        prepareStatement.append("ID, CONTENT, LABEL, MIMETYPE, MODIFIED, ");
        prepareStatement.append("CONTENTSIZE, SEARCHABLE, UTCLOCALTIMESTAMP, UTCSERVERTIMESTAMP, LOGMESSAGE_ID");
        prepareStatement.append("FROM ").append(databasePartition).append("WHERE ");
        // Between date
        prepareStatement.append("UTCSERVERTIMESTAMP BETWEEN ").append(SQLPrepareStatementHelper.toSQLValue(fromDate)).append(" AND ").append(SQLPrepareStatementHelper.toSQLValue(toDate)).append(" ");

        prepareStatement.append("AND ");
        prepareStatement.append("( ");
        prepareStatement.append("CONTENT LIKE ").append(SQLPrepareStatementHelper.toSQLContainsValue(freeText)).append(" OR ");
        prepareStatement.append("LABEL LIKE ").append(SQLPrepareStatementHelper.toSQLContainsValue(freeText)).append(" OR ");
        prepareStatement.append("CONTENTSIZE LIKE ").append(SQLPrepareStatementHelper.toSQLContainsValue(freeText)).append(" OR ");
        prepareStatement.append("MIMETYPE LIKE ").append(SQLPrepareStatementHelper.toSQLContainsValue(freeText)).append(" OR ");
        prepareStatement.append(") ");
        return prepareStatement;

    }
}

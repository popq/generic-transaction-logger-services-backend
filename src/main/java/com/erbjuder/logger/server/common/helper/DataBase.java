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
package com.erbjuder.logger.server.common.helper;

import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_05;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_15;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_06;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_16;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_10;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_07;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_01;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_08;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_11;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_12;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_02;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_17;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_13;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_14;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_03;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_09;
import com.erbjuder.logger.server.entity.impl.LogMessageData_Partition_04;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Stefan Andersson
 */
public class DataBase {

    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_20B = 20;
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_40B = 40;
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_60B = 60;
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_80B = 80;
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_100B = 100;
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_150B = 150;
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_200B = 200;
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_255B = 255;        // TINYTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_64KB = 65535;      // TEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_1MB = 1048576;     // MEDIUMTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_2MB = 2097152;     // MEDIUMTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_3MB = 3145728;     // MEDIUMTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_4MB = 4194304;     // MEDIUMTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_5MB = 5242880;     // MEDIUMTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_10MB = 10485760;   // MEDIUMTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_16MB = 16777215;   // MEDIUMTEXT
    public static final long LOGMESSAGEDATA_CONTENT_MAX_SIZE_4GB = 4294967296l; // LONGTEXT

    public static final Class LOGMESSAGEDATA_PARTITION_01_CLASS = LogMessageData_Partition_01.class;
    public static final Class LOGMESSAGEDATA_PARTITION_02_CLASS = LogMessageData_Partition_02.class;
    public static final Class LOGMESSAGEDATA_PARTITION_03_CLASS = LogMessageData_Partition_03.class;
    public static final Class LOGMESSAGEDATA_PARTITION_04_CLASS = LogMessageData_Partition_04.class;
    public static final Class LOGMESSAGEDATA_PARTITION_05_CLASS = LogMessageData_Partition_05.class;
    public static final Class LOGMESSAGEDATA_PARTITION_06_CLASS = LogMessageData_Partition_06.class;
    public static final Class LOGMESSAGEDATA_PARTITION_07_CLASS = LogMessageData_Partition_07.class;
    public static final Class LOGMESSAGEDATA_PARTITION_08_CLASS = LogMessageData_Partition_08.class;
    public static final Class LOGMESSAGEDATA_PARTITION_09_CLASS = LogMessageData_Partition_09.class; 
    public static final Class LOGMESSAGEDATA_PARTITION_10_CLASS = LogMessageData_Partition_10.class;
    public static final Class LOGMESSAGEDATA_PARTITION_11_CLASS = LogMessageData_Partition_11.class;
    public static final Class LOGMESSAGEDATA_PARTITION_12_CLASS = LogMessageData_Partition_12.class;
    public static final Class LOGMESSAGEDATA_PARTITION_13_CLASS = LogMessageData_Partition_13.class;
    public static final Class LOGMESSAGEDATA_PARTITION_14_CLASS = LogMessageData_Partition_14.class;
    public static final Class LOGMESSAGEDATA_PARTITION_15_CLASS = LogMessageData_Partition_15.class;
    public static final Class LOGMESSAGEDATA_PARTITION_16_CLASS = LogMessageData_Partition_16.class;
    public static final Class LOGMESSAGEDATA_PARTITION_17_CLASS = LogMessageData_Partition_17.class;

    public static final String LOGMESSAGEDATA_PARTITION_01_CONTENT_COLUMN_DEFINITION = "varchar(" + DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_20B + ")";
    public static final String LOGMESSAGEDATA_PARTITION_02_CONTENT_COLUMN_DEFINITION = "varchar(" + DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_40B + ")";
    public static final String LOGMESSAGEDATA_PARTITION_03_CONTENT_COLUMN_DEFINITION = "varchar(" + DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_60B + ")";
    public static final String LOGMESSAGEDATA_PARTITION_04_CONTENT_COLUMN_DEFINITION = "varchar(" + DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_80B + ")";
    public static final String LOGMESSAGEDATA_PARTITION_05_CONTENT_COLUMN_DEFINITION = "varchar(" + DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_100B + ")";
    public static final String LOGMESSAGEDATA_PARTITION_06_CONTENT_COLUMN_DEFINITION = "varchar(" + DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_150B + ")";
    public static final String LOGMESSAGEDATA_PARTITION_07_CONTENT_COLUMN_DEFINITION = "varchar(" + DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_200B + ")";
    public static final String LOGMESSAGEDATA_PARTITION_08_CONTENT_COLUMN_DEFINITION = "TINYTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_09_CONTENT_COLUMN_DEFINITION = "TEXT";
    public static final String LOGMESSAGEDATA_PARTITION_10_CONTENT_COLUMN_DEFINITION = "MEDIUMTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_11_CONTENT_COLUMN_DEFINITION = "MEDIUMTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_12_CONTENT_COLUMN_DEFINITION = "MEDIUMTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_13_CONTENT_COLUMN_DEFINITION = "MEDIUMTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_14_CONTENT_COLUMN_DEFINITION = "MEDIUMTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_15_CONTENT_COLUMN_DEFINITION = "MEDIUMTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_16_CONTENT_COLUMN_DEFINITION = "MEDIUMTEXT";
    public static final String LOGMESSAGEDATA_PARTITION_17_CONTENT_COLUMN_DEFINITION = "LONGTEXT";

    public static final Set<Long> LOGMESSAGEDATA_CONTENT_MAX_SIZES = new HashSet<Long>();
    public static final Set<Class> LOGMESSAGEDATA_PARTITION_CLASSES = new HashSet<Class>();

    static {
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_20B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_40B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_60B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_80B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_100B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_150B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_200B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_255B);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_64KB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_1MB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_2MB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_3MB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_4MB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_5MB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_10MB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_16MB);
        LOGMESSAGEDATA_CONTENT_MAX_SIZES.add(DataBase.LOGMESSAGEDATA_CONTENT_MAX_SIZE_4GB);

    }

    static {
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_01_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_02_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_03_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_04_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_05_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_06_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_07_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_08_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_09_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_10_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_11_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_12_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_13_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_14_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_15_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_16_CLASS);
        LOGMESSAGEDATA_PARTITION_CLASSES.add(LOGMESSAGEDATA_PARTITION_17_CLASS);
    }

}

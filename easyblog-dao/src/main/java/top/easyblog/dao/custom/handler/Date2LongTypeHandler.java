package top.easyblog.dao.custom.handler;


import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

/**
 * @author: frank.huang
 * @date: 2023-02-05 22:42
 */
@MappedJdbcTypes(value = {JdbcType.DATE, JdbcType.TIME, JdbcType.TIMESTAMP})
@MappedTypes(Long.class)
public class Date2LongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long aLong, JdbcType jdbcType) throws SQLException {
        if (jdbcType == JdbcType.DATE) {
            ps.setDate(i, new Date(aLong));
        } else if (jdbcType == JdbcType.TIME) {
            ps.setTime(i, new Time(aLong));
        } else if (jdbcType == JdbcType.TIMESTAMP) {
            ps.setTimestamp(i, new Timestamp(aLong));
        }
    }

    @Override
    public Long getNullableResult(ResultSet rs, String s) throws SQLException {
        return toLong(rs.getObject(s));
    }

    @Override
    public Long getNullableResult(ResultSet rs, int i) throws SQLException {
        return toLong(rs.getObject(i));
    }

    @Override
    public Long getNullableResult(CallableStatement cs, int i) throws SQLException {
        return toLong(cs.getObject(i));
    }

    private Long toLong(Object value) {
        if (value instanceof Date) {
            return ((Date) value).getTime();
        } else if (value instanceof Time) {
            return ((Time) value).getTime();
        } else if (value instanceof Timestamp) {
            return ((Timestamp) value).getTime();
        }
        return null;
    }
}

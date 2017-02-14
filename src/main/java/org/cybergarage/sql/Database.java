package org.cybergarage.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import org.cybergarage.util.Debug;

public abstract class Database {
    private Connection con;
    private ResultSet rs;
    private Statement stmt;

    public abstract boolean open(String str, String str2, String str3, String str4);

    public Database() {
        setConnection(null);
        setStatement(null);
        setResultSet(null);
    }

    protected void setConnection(Connection c) {
        this.con = c;
    }

    private Connection getConnection() {
        return this.con;
    }

    private void setStatement(Statement s) {
        this.stmt = s;
    }

    private Statement getStatement() {
        return this.stmt;
    }

    private void setResultSet(ResultSet r) {
        this.rs = r;
    }

    private ResultSet getResultSet() {
        return this.rs;
    }

    public void close() {
        Connection con = getConnection();
        if (con != null) {
            try {
                con.close();
                setConnection(null);
            } catch (Exception e) {
                Debug.warning(e);
            }
        }
    }

    public boolean query(String sql) {
        try {
            Statement stmt = getStatement();
            if (stmt != null) {
                stmt.close();
            }
            ResultSet rs = getResultSet();
            if (rs != null) {
                rs.close();
            }
            Connection con = getConnection();
            if (con == null) {
                return false;
            }
            stmt = con.createStatement();
            setStatement(stmt);
            setResultSet(stmt.executeQuery(sql));
            return true;
        } catch (Exception e) {
            Debug.warning(e);
            return false;
        }
    }

    public boolean fetch() {
        boolean fetchRet = false;
        try {
            ResultSet rs = getResultSet();
            if (rs == null) {
                return false;
            }
            fetchRet = rs.next();
            if (!fetchRet) {
                Statement stmt = getStatement();
                if (stmt != null) {
                    stmt.close();
                    setStatement(null);
                }
                rs.close();
                setResultSet(null);
            }
            return fetchRet;
        } catch (Exception e) {
            Debug.warning(e);
        }
    }

    public String getString(String name) {
        try {
            ResultSet rs = getResultSet();
            if (rs == null) {
                return "";
            }
            return new String(rs.getBytes(name));
        } catch (Exception e) {
            Debug.warning(e);
            return "";
        }
    }

    public String getString(int n) {
        try {
            ResultSet rs = getResultSet();
            if (rs == null) {
                return "";
            }
            return new String(rs.getBytes(n));
        } catch (Exception e) {
            Debug.warning(e);
            return "";
        }
    }

    public int getInteger(String name) {
        int i = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                i = rs.getInt(name);
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return i;
    }

    public int getInteger(int n) {
        int i = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                i = rs.getInt(n);
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return i;
    }

    public long getLong(String name) {
        long j = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                j = rs.getLong(name);
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return j;
    }

    public long getLong(int n) {
        long j = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                j = rs.getLong(n);
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return j;
    }

    public long getTimestamp(String name) {
        long j = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                j = rs.getTimestamp(name).getTime();
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return j;
    }

    public long getTimestamp(int n) {
        long j = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                j = rs.getTimestamp(n).getTime();
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return j;
    }

    public long getDate(String name) {
        long j = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                j = rs.getDate(name).getTime();
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return j;
    }

    public long getDate(int n) {
        long j = 0;
        try {
            ResultSet rs = getResultSet();
            if (rs != null) {
                j = rs.getDate(n).getTime();
            }
        } catch (Exception e) {
            Debug.warning(e);
        }
        return j;
    }

    public int update(String sql) {
        int numOfUpdated = 0;
        try {
            Statement stmt = getStatement();
            if (stmt != null) {
                stmt.close();
                setStatement(null);
            }
            ResultSet rs = getResultSet();
            if (rs != null) {
                rs.close();
                setResultSet(null);
            }
            Connection con = getConnection();
            if (con == null) {
                return 0;
            }
            stmt = con.createStatement();
            numOfUpdated = stmt.executeUpdate(sql);
            stmt.close();
            return numOfUpdated;
        } catch (Exception e) {
            Debug.warning(e);
        }
    }
}

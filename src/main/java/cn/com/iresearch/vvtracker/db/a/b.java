package cn.com.iresearch.vvtracker.db.a;

public final class b extends a {
    private String a = null;

    public b(String str) {
        this.a = str;
    }

    public final void printStackTrace() {
        if (this.a != null) {
            System.err.println(this.a);
        }
        super.printStackTrace();
    }
}

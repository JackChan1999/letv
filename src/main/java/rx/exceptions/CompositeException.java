package rx.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import rx.annotations.Experimental;

public final class CompositeException extends RuntimeException {
    private static final long serialVersionUID = 3026362227162912146L;
    private Throwable cause;
    private final List<Throwable> exceptions;
    private final String message;

    @Deprecated
    public CompositeException(String messagePrefix, Collection<? extends Throwable> errors) {
        this.cause = null;
        Set<Throwable> deDupedExceptions = new LinkedHashSet();
        List<Throwable> _exceptions = new ArrayList();
        if (errors != null) {
            for (Throwable ex : errors) {
                if (ex instanceof CompositeException) {
                    deDupedExceptions.addAll(((CompositeException) ex).getExceptions());
                } else if (ex != null) {
                    deDupedExceptions.add(ex);
                } else {
                    deDupedExceptions.add(new NullPointerException());
                }
            }
        } else {
            deDupedExceptions.add(new NullPointerException());
        }
        _exceptions.addAll(deDupedExceptions);
        this.exceptions = Collections.unmodifiableList(_exceptions);
        this.message = this.exceptions.size() + " exceptions occurred. ";
    }

    public CompositeException(Collection<? extends Throwable> errors) {
        this(null, errors);
    }

    @Experimental
    public CompositeException(Throwable... errors) {
        this.cause = null;
        Set<Throwable> deDupedExceptions = new LinkedHashSet();
        List<Throwable> _exceptions = new ArrayList();
        if (errors != null) {
            for (Throwable ex : errors) {
                if (ex instanceof CompositeException) {
                    deDupedExceptions.addAll(((CompositeException) ex).getExceptions());
                } else if (ex != null) {
                    deDupedExceptions.add(ex);
                } else {
                    deDupedExceptions.add(new NullPointerException());
                }
            }
        } else {
            deDupedExceptions.add(new NullPointerException());
        }
        _exceptions.addAll(deDupedExceptions);
        this.exceptions = Collections.unmodifiableList(_exceptions);
        this.message = this.exceptions.size() + " exceptions occurred. ";
    }

    public List<Throwable> getExceptions() {
        return this.exceptions;
    }

    public String getMessage() {
        return this.message;
    }

    public synchronized Throwable getCause() {
        Throwable e;
        if (this.cause == null) {
            CompositeExceptionCausalChain _cause = new CompositeExceptionCausalChain();
            Set<Throwable> seenCauses = new HashSet();
            Throwable chain = _cause;
            for (Throwable e2 : this.exceptions) {
                if (!seenCauses.contains(e2)) {
                    seenCauses.add(e2);
                    for (Throwable child : getListOfCauses(e2)) {
                        if (seenCauses.contains(child)) {
                            e2 = new RuntimeException("Duplicate found in causal chain so cropping to prevent loop ...");
                        } else {
                            seenCauses.add(child);
                        }
                    }
                    try {
                        chain.initCause(e2);
                        chain = chain.getCause();
                    } catch (Throwable th) {
                        chain = e2;
                    }
                }
            }
            this.cause = _cause;
        }
        return this.cause;
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream s) {
        printStackTrace(new WrappedPrintStream(s));
    }

    public void printStackTrace(PrintWriter s) {
        printStackTrace(new WrappedPrintWriter(s));
    }

    private void printStackTrace(PrintStreamOrWriter s) {
        StringBuilder bldr = new StringBuilder();
        bldr.append(this).append("\n");
        for (StackTraceElement myStackElement : getStackTrace()) {
            bldr.append("\tat ").append(myStackElement).append("\n");
        }
        int i = 1;
        for (Throwable ex : this.exceptions) {
            bldr.append("  ComposedException ").append(i).append(" :").append("\n");
            appendStackTrace(bldr, ex, "\t");
            i++;
        }
        synchronized (s.lock()) {
            s.println(bldr.toString());
        }
    }

    private void appendStackTrace(StringBuilder bldr, Throwable ex, String prefix) {
        bldr.append(prefix).append(ex).append("\n");
        for (StackTraceElement stackElement : ex.getStackTrace()) {
            bldr.append("\t\tat ").append(stackElement).append("\n");
        }
        if (ex.getCause() != null) {
            bldr.append("\tCaused by: ");
            appendStackTrace(bldr, ex.getCause(), "");
        }
    }

    private List<Throwable> getListOfCauses(Throwable ex) {
        List<Throwable> list = new ArrayList();
        Throwable root = ex.getCause();
        if (root != null) {
            while (true) {
                list.add(root);
                if (root.getCause() == null) {
                    break;
                }
                root = root.getCause();
            }
        }
        return list;
    }
}

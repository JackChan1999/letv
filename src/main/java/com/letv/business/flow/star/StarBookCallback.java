package com.letv.business.flow.star;

import java.util.Set;

public interface StarBookCallback {
    void onBookedPrograms(Set<String> set);

    void reAskData();
}

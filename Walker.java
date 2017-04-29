package Lab5;

import Lab5.Baby;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Vladimir on 15/02/2017.
 */
public interface Walker {
        void walk();

        int compareTo(@NotNull Baby b);
}

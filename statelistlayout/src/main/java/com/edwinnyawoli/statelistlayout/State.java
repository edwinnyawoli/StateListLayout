package com.edwinnyawoli.statelistlayout;

/**
 * States define the various content that the StateListLayout can show.
 */

public class State {
    public static final State UNKNOWN = new State("unknown");
    public static final State EMPTY = new State("empty");
    public static final State PROGRESS = new State("progress");
    public static final State CONTENT = new State("content");
    public static final State ERROR = new State("error");
    private String name;


    public State(String name) {
        if (name == null || name == "")
            throw new IllegalArgumentException("Name cannot be null or empty.");

        this.name = name.toLowerCase().trim();
    }

    /**
     * Creates a state object from the name specified.
     *
     * @param name         The name of the state to create.
     * @param defaultState The default state that should be returned if creation fails.
     * @return The state object with the specified name or the default state if an exception
     * occured.
     */
    public static State fromName(String name, State defaultState) {
        try {
            State state = new State(name);
            return state;
        } catch (Exception e) {
            return defaultState;
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;

        State state = (State) o;

        return name.equals(state.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

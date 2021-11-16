package by.alex.testing.controller;

public class ViewResolver {
    public enum ResolveAction {
        FORWARD, REDIRECT
    }

    private String view;
    private ResolveAction resolveAction;

    public ViewResolver() {
    }

    public ViewResolver(String view) {
        this.view = view;
        resolveAction = ResolveAction.FORWARD;
    }

    public ViewResolver(String view, ResolveAction action) {
        this.view = view;
        this.resolveAction = action;
    }

    public String getView() {
        return view;
    }

    public void setView(final String view) {
        this.view = view;
    }

    public ResolveAction getResolveAction() {
        return resolveAction;
    }

    public void setResolveAction(ResolveAction action) {
        this.resolveAction = action;
    }

    public void forward(final String view) {
        setView(view);
        resolveAction = ResolveAction.FORWARD;
    }

    public void redirect(final String view) {
        setView(view);
        resolveAction = ResolveAction.REDIRECT;
    }
}

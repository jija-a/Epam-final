package by.alex.testing.controller;

/**
 * {@link by.alex.testing.controller.command.Command} return value.
 * Consists of {@link PageConstant} jsp name and {@link ResolveAction}.
 */
public class ViewResolver {
    public enum ResolveAction {
        /**
         * Forward action.
         *
         * @see javax.servlet.http.HttpServlet
         * @see javax.servlet.RequestDispatcher#forward(
         *javax.servlet.ServletRequest, javax.servlet.ServletResponse)
         */
        FORWARD,
        /**
         * Redirect action.
         *
         * @see javax.servlet.http.HttpServlet
         * @see javax.servlet.RequestDispatcher#forward(
         *javax.servlet.ServletRequest, javax.servlet.ServletResponse)
         */
        REDIRECT
    }

    /**
     * View jsp page name.
     *
     * @see PageConstant
     */
    private String view;

    /**
     * {@link ResolveAction} name.
     */
    private ResolveAction resolveAction;

    /**
     * Default constructor.
     */
    public ViewResolver() {
    }

    /**
     * @param view {@link PageConstant} jsp name
     */
    public ViewResolver(final String view) {
        this.view = view;
        resolveAction = ResolveAction.FORWARD;
    }

    /**
     * @param view   {@link PageConstant} jsp name
     * @param action {@link ResolveAction}
     */
    public ViewResolver(final String view, final ResolveAction action) {
        this.view = view;
        this.resolveAction = action;
    }

    /**
     * @return {@link String} jsp view name
     */
    public String getView() {
        return view;
    }

    /**
     * @param view {@link String} jsp view name
     */
    public void setView(final String view) {
        this.view = view;
    }

    /**
     * @return {@link ResolveAction}
     */
    public ResolveAction getResolveAction() {
        return resolveAction;
    }

    /**
     * @param action {@link ResolveAction}
     */
    public void setResolveAction(final ResolveAction action) {
        this.resolveAction = action;
    }

    /**
     * Set default {@link ResolveAction#FORWARD}.
     *
     * @param view {@link PageConstant} jsp name
     */
    public void forward(final String view) {
        setView(view);
        resolveAction = ResolveAction.FORWARD;
    }

    /**
     * Set default {@link ResolveAction#REDIRECT}.
     *
     * @param view {@link PageConstant} jsp name
     */
    public void redirect(final String view) {
        setView(view);
        resolveAction = ResolveAction.REDIRECT;
    }
}

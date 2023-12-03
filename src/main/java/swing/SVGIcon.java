package swing;


public class SVGIcon implements javax.swing.Icon {
    private org.apache.batik.gvt.GraphicsNode svgIcon = null;

    /**
     * Method to fetch the SVG icon from a url
     *
     * @param url the url from which to fetch the SVG icon
     *
     * @return a graphics node object that can be used for painting
     */
    public SVGIcon(java.net.URL url) throws Exception
    {
        String xmlParser = org.apache.batik.util.XMLResourceDescriptor.getXMLParserClassName();
        org.apache.batik.anim.dom.SAXSVGDocumentFactory df = new org.apache.batik.anim.dom.SAXSVGDocumentFactory(xmlParser);
        org.w3c.dom.svg.SVGDocument doc = df.createSVGDocument(url.toString());
        org.apache.batik.bridge.UserAgent userAgent = new org.apache.batik.bridge.UserAgentAdapter();
        org.apache.batik.bridge.DocumentLoader loader = new org.apache.batik.bridge.DocumentLoader(userAgent);
        org.apache.batik.bridge.BridgeContext ctx = new org.apache.batik.bridge.BridgeContext(userAgent, loader);
        ctx.setDynamicState(org.apache.batik.bridge.BridgeContext.DYNAMIC);
        org.apache.batik.bridge.GVTBuilder builder = new org.apache.batik.bridge.GVTBuilder();
        this.svgIcon = builder.build(ctx, doc);
    }


    /**
     * Method to paint the icon using Graphics2D. Note that the scaling factors have nothing to do with the zoom
     * operation, the scaling factors set the size your icon relative to the other objects on your canvas.
     *
     * @param g the graphics context used for drawing
     *
     * @param svgIcon the graphics node object that contains the SVG icon information
     *
     * @param x the X coordinate of the top left corner of the icon
     *
     * @param y the Y coordinate of the top left corner of the icon
     *
     * @param scaleX the X scaling to be applied to the icon before drawing
     *
     * @param scaleY the Y scaling to be applied to the icon before drawing
     */
    private void paintSvgIcon(
            java.awt.Graphics2D g,
            int x, int y,
            double scaleX, double scaleY
    )
    {
        java.awt.geom.AffineTransform transform = new java.awt.geom.AffineTransform(scaleX, 0.0, 0.0, scaleY, x, y);
        svgIcon.setTransform(transform);
        svgIcon.paint(g);
    }

    public int getIconHeight()
    {
        return (int)svgIcon.getPrimitiveBounds().getHeight();
    }

    public int getIconWidth()
    {
        return (int)svgIcon.getPrimitiveBounds().getWidth();
    }

    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y)
    {
        paintSvgIcon((java.awt.Graphics2D)g,x,y,1,1);
    }

    public  static void main(String args[]) throws Exception
    {
        javax.swing.JOptionPane.showMessageDialog(null,
                new javax.swing.JLabel(
                        new SVGIcon(
                                new java.net.URL("http://upload.wikimedia.org/wikipedia/commons/8/87/PCR.svg")
                        )));
    }
}

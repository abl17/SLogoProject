This folder contains all saved workspaces using DOM XML

TO TEST:

	private static final String SAVE_SUCCESS_NOTIFICATION = "Congratulations! Your file can be saved successfully.";
	testOutput(transformer, source);

	/**
	 * Tests output of a DOM source file to a given output, i.e. tests saving to file by
	 * displaying in Std.out.
	 * 
	 * @param transformer
	 * @param source
	 */
	private static void testOutput (Transformer transformer, DOMSource source) {
		StreamResult result = new StreamResult(System.out);
		try {
			transformer.transform(source, result);
			System.out.println();
			System.out.println(SAVE_SUCCESS_NOTIFICATION);
		} catch (TransformerException e) {
			System.err.println(e.getMessage());
		}
	}
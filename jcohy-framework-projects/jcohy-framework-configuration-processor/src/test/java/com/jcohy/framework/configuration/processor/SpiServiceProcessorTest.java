// package com.jcohy.framework.configuration.processor;
//
// import java.io.File;
// import java.io.IOException;
// import java.util.List;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
//
// import com.jcohy.framework.configuration.processor.spi.StartupServiceTestOne;
// import com.jcohy.framework.configuration.processor.spi.StartupServiceTestTwo;
// import com.jcohy.framework.configuration.processor.spi.TestSpiServiceProcessor;
//
// import static org.assertj.core.api.Assertions.assertThat;
//
/// **
// * 描述: .
// *
// * <p>
// * Copyright © 2022
// * <a href="https://www.jcohy.com" target= "_blank">https://www.jcohy.com</a>
// *
// * @author jiac
// * @version 2022.0.1 2022/14/22:18:23
// * @since 2022.0.1
// */
// class SpiServiceProcessorTest {
//
// private TestCompiler compiler;
//
// @BeforeEach
// void createCompiler() throws IOException {
// this.compiler = new TestCompiler(new File("src/test/resources"));
// }
//
// @Test
// void annotatedClass() throws IOException {
// List<String> result = compile(StartupServiceTestOne.class,
// StartupServiceTestTwo.class);
// System.out.println(result);
// assertThat(result).hasSize(2);
// }
//
// private List<String> compile(Class<?>... types) throws IOException {
// return process(types).getWrittenProperties();
// }
//
// private TestSpiServiceProcessor process(Class<?>... types) {
// TestSpiServiceProcessor processor = new
// TestSpiServiceProcessor(this.compiler.getOutputLocation());
// this.compiler.getTask(types).call(processor);
// return processor;
// }
//
// }

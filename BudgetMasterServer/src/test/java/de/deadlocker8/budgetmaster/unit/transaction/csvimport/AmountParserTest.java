package de.deadlocker8.budgetmaster.unit.transaction.csvimport;

import de.deadlocker8.budgetmaster.transactions.csvimport.AmountParser;
import de.deadlocker8.budgetmaster.unit.helpers.LocalizedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

class AmountParserTest
{
	@Test
	void test_dot_positive_noCurrency()
	{
		assertThat(AmountParser.parse("12.03", '.', ','))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_dot_negative_noCurrency()
	{
		assertThat(AmountParser.parse("-18.41", '.', ','))
				.isPresent()
				.get().isEqualTo(-1841);
	}

	@Test
	void test_dot_negativeWithSpace_noCurrency()
	{
		assertThat(AmountParser.parse("- 200.30", '.', ','))
				.isPresent()
				.get().isEqualTo(-20030);
	}

	@Test
	void test_dot_positive_currency()
	{
		assertThat(AmountParser.parse("12.03 €", '.', ','))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_dot_negative_currency()
	{
		assertThat(AmountParser.parse("-18.41€", '.', ','))
				.isPresent()
				.get().isEqualTo(-1841);
	}

	@Test
	void test_dot_positiveWithSign_noCurrency()
	{
		assertThat(AmountParser.parse("+12.03", '.', ','))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_dot_positiveWithSignWithSpace_noCurrency()
	{
		assertThat(AmountParser.parse("+ 12.03", '.', ','))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_comma_positive_noCurrency()
	{
		assertThat(AmountParser.parse("12,03", ',', '.'))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_comma_negative_noCurrency()
	{
		assertThat(AmountParser.parse("-18,41", ',', '.'))
				.isPresent()
				.get().isEqualTo(-1841);
	}

	@Test
	void test_comma_negativeWithSpace_noCurrency()
	{
		assertThat(AmountParser.parse("- 200,30", ',', '.'))
				.isPresent()
				.get().isEqualTo(-20030);
	}

	@Test
	void test_comma_positive_currency()
	{
		assertThat(AmountParser.parse("12,03 €", ',', '.'))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_comma_negative_currency()
	{
		assertThat(AmountParser.parse("-18,41€", ',', '.'))
				.isPresent()
				.get().isEqualTo(-1841);
	}

	@Test
	void test_comma_positiveWithSign_noCurrency()
	{
		assertThat(AmountParser.parse("+12,03", ',', '.'))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_comma_positiveWithSignWithSpace_noCurrency()
	{
		assertThat(AmountParser.parse("+12,03", ',', '.'))
				.isPresent()
				.get().isEqualTo(1203);
	}

	@Test
	void test_invalid_null()
	{
		assertThat(AmountParser.parse(null, '.', ','))
				.isEmpty();
	}

	@Test
	void test_invalid_empty()
	{
		assertThat(AmountParser.parse("", '.', ','))
				.isEmpty();
	}

	@Test
	void test_invalid_empty2()
	{
		assertThat(AmountParser.parse("    ", '.', ','))
				.isEmpty();
	}

	@Test
	void test_invalid()
	{
		assertThat(AmountParser.parse("abc.42€", '.', ','))
				.isEmpty();
	}

	@Test
	void test_integer_positive_noCurrency()
	{
		assertThat(AmountParser.parse("12", '.', ','))
				.isPresent()
				.get().isEqualTo(1200);
	}

	@Test
	void test_integer_negative_noCurrency()
	{
		assertThat(AmountParser.parse("-18", '.', ','))
				.isPresent()
				.get().isEqualTo(-1800);
	}

	@Test
	void test_integer_negativeWithSpace_noCurrency()
	{
		assertThat(AmountParser.parse("- 200", '.', ','))
				.isPresent()
				.get().isEqualTo(-20000);
	}

	@Test
	void test_integer_positive_currency()
	{
		assertThat(AmountParser.parse("12 €", '.', ','))
				.isPresent()
				.get().isEqualTo(1200);
	}

	@Test
	void test_integer_negative_currency()
	{
		assertThat(AmountParser.parse("-18€", '.', ','))
				.isPresent()
				.get().isEqualTo(-1800);
	}

	@Test
	void test_integer_positiveWithSign_noCurrency()
	{
		assertThat(AmountParser.parse("+12", '.', ','))
				.isPresent()
				.get().isEqualTo(1200);
	}

	@Test
	void test_integer_positiveWithSignWithSpace_noCurrency()
	{
		assertThat(AmountParser.parse("+ 12", '.', ','))
				.isPresent()
				.get().isEqualTo(1200);
	}

	@Test
	void test_thousandsDelimiter_integer()
	{
		assertThat(AmountParser.parse("1,234", '.', ','))
				.isPresent()
				.get().isEqualTo(123400);
	}

	@Test
	void test_thousandsDelimiter_dot()
	{
		assertThat(AmountParser.parse("1,234.03", '.', ','))
				.isPresent()
				.get().isEqualTo(123403);
	}

	@Test
	void test_thousandsDelimiter_withCurrency()
	{
		assertThat(AmountParser.parse("1,234.03 €", '.', ','))
				.isPresent()
				.get().isEqualTo(123403);
	}

	@Test
	void test_thousandsDelimiter_negative()
	{
		assertThat(AmountParser.parse("-1,234.03 €", '.', ','))
				.isPresent()
				.get().isEqualTo(-123403);
	}

	@Test
	void test_thousandsDelimiter_specialDelimiter()
	{
		assertThat(AmountParser.parse("-1.234,03 €", ',', '.'))
				.isPresent()
				.get().isEqualTo(-123403);
	}

	@Test
	void test_thousandsDelimiter_specialDelimiter_invalid()
	{
		assertThat(AmountParser.parse("-234.03 €", 'e', 't'))
				.isEmpty();
	}

	@Test
	void test_thousandsDelimiter_specialDelimiter_valid()
	{
		assertThat(AmountParser.parse("-1t234e03 €", 'e', 't'))
				.isPresent()
				.get().isEqualTo(-123403);
	}

	@Test
	void test_thousandsDelimiter_bigNumber()
	{
		assertThat(AmountParser.parse("-1.234.567,03 €", ',', '.'))
				.isPresent()
				.get().isEqualTo(-123456703);
	}

	@Test
	void test_floatingPoint()
	{
		assertThat(AmountParser.parse("-9,7 €", ',', '.'))
				.isPresent()
				.get().isEqualTo(-970);
	}
}

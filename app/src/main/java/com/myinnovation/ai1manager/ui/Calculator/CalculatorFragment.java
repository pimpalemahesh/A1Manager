package com.myinnovation.ai1manager.ui.Calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.myinnovation.ai1manager.databinding.FragmentCalculatorBinding;
import com.myinnovation.ai1manager.databinding.FragmentHomeBinding;

public class CalculatorFragment extends Fragment {

    private FragmentCalculatorBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (binding.tvmain.getText().equals("") || binding.tvmain.getText() == null) {
            binding.tvmain.setText("0");
        }
        if (binding.tvsec.getText().equals("") || binding.tvsec.getText() == null) {
            binding.tvsec.setText("0");
        }

        binding.b1.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "1");
        });

        binding.b2.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "2");
        });

        binding.b3.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "3");
        });

        binding.b4.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "4");
        });

        binding.b5.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "5");
        });

        binding.b6.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "6");
        });

        binding.b7.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "7");
        });

        binding.b8.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "8");
        });

        binding.b9.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "9");
        });

        binding.b0.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "0");
        });

        binding.bmul.setOnClickListener(v -> {
            validateSigns();
            if (binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1) == "(" ||
                    binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1) == ")") {
                binding.tvmain.setText(binding.tvmain.getText().toString());
            } else{
                binding.tvmain.setText(binding.tvmain.getText() + "×");
            }
        });

        binding.bdiv.setOnClickListener(v -> {
            validateSigns();
            if (binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1) == "(" ||
                    binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1) == ")") {
                binding.tvmain.setText(binding.tvmain.getText().toString());
            } else{
                binding.tvmain.setText(binding.tvmain.getText() + "÷");
            }
        });

        binding.bplus.setOnClickListener(v -> {
            validateSigns();
            binding.tvmain.setText(binding.tvmain.getText() + "+");
        });

        binding.bmin.setOnClickListener(v -> {
            validateSigns();
            binding.tvmain.setText(binding.tvmain.getText() + "-");
        });

        binding.bpow.setOnClickListener(v -> {
            validateSigns();
            if (binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1) == "(" ||
                    binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1) == ")") {
                binding.tvmain.setText(binding.tvmain.getText().toString());
            } else{
                binding.tvmain.setText(binding.tvmain.getText() + "^");
            }
        });

        binding.bsin.setOnClickListener(v -> {
            checkSign();
            binding.tvmain.setText(binding.tvmain.getText() + "sin(");
        });

        binding.bcos.setOnClickListener(v -> {
            checkSign();
            binding.tvmain.setText(binding.tvmain.getText() + "cos(");
        });

        binding.btan.setOnClickListener(v -> {
            checkSign();
            binding.tvmain.setText(binding.tvmain.getText() + "tan(");
        });

        binding.blog.setOnClickListener(v -> {
            checkSign();
            binding.tvmain.setText(binding.tvmain.getText() + "log(");
        });

        binding.bln.setOnClickListener(v -> {
            checkSign();
            binding.tvmain.setText(binding.tvmain.getText() + "ln(");
        });

        binding.bdot.setOnClickListener(v -> {
            binding.tvmain.setText(binding.tvmain.getText() + ".");
        });

        binding.bpi.setOnClickListener(v -> {
            checkSign();
            binding.tvmain.setText(binding.tvmain.getText() + String.valueOf(Math.PI));
        });

        binding.bb1.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + "(");
        });

        binding.bb2.setOnClickListener(v -> {
            checkEmpty();
            binding.tvmain.setText(binding.tvmain.getText() + ")");
        });

        binding.bsqrt.setOnClickListener(v -> {
            binding.tvsec.setText("√" + binding.tvmain.getText().toString());
            binding.tvmain.setText(String.valueOf(Math.sqrt(Double.parseDouble(binding.tvmain.getText().toString()))));
        });

        binding.bfact.setOnClickListener(v -> {
            binding.tvsec.setText(binding.tvmain.getText() + "!");
            try{
                double number = Double.parseDouble(binding.tvmain.getText().toString());
                binding.tvmain.setText(String.valueOf(calculateFact(number)));
            } catch (Exception e){
                binding.tvmain.setText("Infinity");
            }

        });

        binding.binv.setOnClickListener(v -> {
            binding.tvsec.setText("1/" + binding.tvmain.getText());
            double number = Double.parseDouble(binding.tvmain.getText().toString());
            double answer = 1 / number;
            binding.tvmain.setText(String.valueOf(answer));
        });

        binding.bac.setOnClickListener(v -> {
            binding.tvmain.setText("0");
            binding.tvsec.setText("0");
        });

        binding.bc.setOnClickListener(v -> {
            if (binding.tvmain.getText().length() == 1) {
                binding.tvmain.setText("0");
            } else {
                binding.tvmain.setText(binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1));
            }
        });

        binding.bequal.setOnClickListener(v -> {

            String number = binding.tvmain.getText().toString();
            if (number.charAt(binding.tvmain.getText().length() - 1) < '0' || number.charAt(binding.tvmain.getText().length() - 1) > '9') {
                binding.tvmain.setText(binding.tvmain.getText().toString() + "0");
            }
            if (number.equals("Infinity")) {
                binding.tvmain.setText("Infinity");
                return;
            } else {
                binding.tvsec.setText(binding.tvmain.getText().toString());
                binding.tvmain.setText(String.valueOf(Calculate(number)));
            }
        });


        return root;
    }

    private static Double Calculate(final String number) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < number.length()) ? number.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < number.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('×')) x *= parseFactor(); // multiplication
                    else if (eat('÷')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(number.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = number.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("ln")) x = Math.log(x);
                    else if (func.equals("log")) x = Math.log10(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    private void checkEmpty() {
        if (binding.tvmain.getText().length() == 1 && binding.tvmain.getText().toString().equals("0")) {
            binding.tvmain.setText("");
        } else return;
    }

    private void checkSign() {

        if (binding.tvmain.getText().length() == 1 && binding.tvmain.getText().toString().equals("0")) {
            binding.tvmain.setText("");
        } else if (binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) != '+' ||
                binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) != '-' ||
                binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) != '×' ||
                binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) != '÷') {
            binding.tvmain.setText(binding.tvmain.getText().toString() + "×");
        } else return;
    }

    private void validateSigns() {
        if (    binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) == '+' ||
                binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) == '-' ||
                binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) == '^' ||
                binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) == '÷' ||
                binding.tvmain.getText().charAt(binding.tvmain.getText().length() - 1) == '×') {
            binding.tvmain.setText(binding.tvmain.getText().toString().substring(0, binding.tvmain.getText().length() - 1));
        }
    }

    private double calculateFact(double number) {
        return (number == 0 || number == 1) ? 1 : number * calculateFact(number - 1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
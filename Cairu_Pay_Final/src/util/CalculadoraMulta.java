package util;

import model.Divida;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class CalculadoraMulta {
    
    public static double calcularValorComMulta(Divida divida, Date dataPagamento) {
        double valorOriginal = divida.getValorDivida();
        Date dataAtualizacao = divida.getDataAtualizacao();
        
        // Se a data de pagamento é maior que a data de atualização, calcular multa
        if (dataPagamento.after(dataAtualizacao)) {
            long diasAtraso = calcularDiasAtraso(dataAtualizacao, dataPagamento);
            
            // Multa: 2% + 0,35% ao dia
            double multa = valorOriginal * 0.02; // 2% de multa
            double juros = valorOriginal * 0.0035 * diasAtraso; // 0,35% ao dia
            
            return valorOriginal + multa + juros;
        }
        
        return valorOriginal;
    }
    
    private static long calcularDiasAtraso(Date dataInicio, Date dataFim) {
        long diffInMillies = Math.abs(dataFim.getTime() - dataInicio.getTime());
        return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}


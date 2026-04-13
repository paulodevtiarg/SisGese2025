package br.com.sysgese.utils;

public class StatusUtil {

    private StatusUtil() {
        // evita instanciação
    }

    public static Integer[] parseStatusFiltro(String statusFiltro) {

        if (statusFiltro == null) {
            return new Integer[]{0, 1};
        }

        if ("ativos".equalsIgnoreCase(statusFiltro) || "1".equals(statusFiltro)) {
            return new Integer[]{1};
        }

        if ("inativos".equalsIgnoreCase(statusFiltro) || "0".equals(statusFiltro)) {
            return new Integer[]{0};
        }

        return new Integer[]{0, 1};
    }
}

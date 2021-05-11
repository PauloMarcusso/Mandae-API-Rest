package com.api.mandae.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    public @interface Cozinhas {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("@mandaeSecurity.podeConsultarCozinhas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }
    }

    public @interface Restaurantes {

        @PreAuthorize("@mandaeSecurity.podeGerenciarCadastroRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarCadastro {
        }

        @PreAuthorize("@mandaeSecurity.podeConsultarRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

        @PreAuthorize("@mandaeSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarFuncionamento {
        }
    }

    public @interface Pedidos {

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDO') or " +
                "@mandaeSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or" +
                "@mandaeSecurity.gerenciaRestaurante(returnObject.restaurante.id)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeBuscar {
        }

        @PreAuthorize("@mandaeSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodePesquisar {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeCriar {
        }

        @PreAuthorize("@mandaeSecurity.podeGerenciarPedidos(#codigoPedido)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeGerenciarPedidos {
        }
    }

    public @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("@mandaeSecurity.podeConsultarFormasPagamento()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

    }

    public @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("@mandaeSecurity.podeConsultarCidades()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

    }

    public @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }

        @PreAuthorize("@mandaeSecurity.podeConsultarEstados()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

    }

    public @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "@mandaeSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeAlterarPropriaSenha {
        }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
                + "@mandaeSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeAlterarUsuario {
        }

        @PreAuthorize("@mandaeSecurity.podeEditarUsuariosGruposPermissoes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeEditar {
        }


        @PreAuthorize("@mandaeSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }
    }

    public @interface Estatisticas {

        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        public @interface PodeConsultar {
        }

    }
}

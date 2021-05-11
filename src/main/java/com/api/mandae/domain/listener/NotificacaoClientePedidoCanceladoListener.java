package com.api.mandae.domain.listener;

import com.api.mandae.domain.event.PedidoCanceladoEvent;
import com.api.mandae.domain.event.PedidoConfirmadoEvent;
import com.api.mandae.domain.model.Pedido;
import com.api.mandae.domain.service.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class NotificacaoClientePedidoCanceladoListener {

    @Autowired
    private EnvioEmailService envioEmail;

    @TransactionalEventListener
    public void aoCancelarPedido(PedidoCanceladoEvent event) {

        Pedido pedido = event.getPedido();

        var mensagem = EnvioEmailService
                .Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido Cancelado")
                .corpo("emails/pedido-cancelado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }
}

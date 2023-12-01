package br.com.recoleta.app.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

	private final SessionRegistry sessionRegistry;

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response) throws SessionAuthenticationException {

		String jSessionId = request.getRequestedSessionId();

		if (jSessionId != null) {
			// Verifica se a sessão atual já está registrada
			SessionInformation sessionInformation = sessionRegistry.getSessionInformation(jSessionId);

			if (sessionInformation == null) {
				// Se a sessão não estiver registrada, registra a sessão manualmente
				sessionRegistry.registerNewSession(jSessionId, authentication.getPrincipal());
			} else {
				// Se a sessão já estiver registrada, atualiza os detalhes da sessão
				sessionInformation.refreshLastRequest();
			}
		}
	}
}

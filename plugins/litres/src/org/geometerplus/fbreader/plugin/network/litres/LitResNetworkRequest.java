/*
 * Copyright (C) 2010 Geometer Plus <contact@geometerplus.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 */

package org.geometerplus.fbreader.plugin.network.litres;

import java.io.InputStream;
import java.io.IOException;
import java.net.URLConnection;

import org.geometerplus.zlibrary.core.network.ZLNetworkException;
import org.geometerplus.zlibrary.core.network.ZLNetworkRequest;

public class LitResNetworkRequest extends ZLNetworkRequest {
	public final LitResAuthenticationXMLReader Reader;

	public LitResNetworkRequest(String url, String sslCertificate, LitResAuthenticationXMLReader reader) {
		super(url, sslCertificate);
		Reader = reader;
	}

	@Override
	public void handleStream(URLConnection connection, InputStream inputStream) throws IOException, ZLNetworkException {
		Reader.read(inputStream);
		ZLNetworkException e = Reader.getException();
		if (e != null) {
			throw e;
		}
	}
}

export interface SocketTCPPlugin {
  echo(options: { value: string }): Promise<{value: string}>;
  connect(options: { ipAddress: string, port: number }): Promise<{success: boolean, client: number}>;
  disconnect(options: { client: number }): Promise<{success: boolean, client: number}>;
  send(options: { data: string, client: number }): Promise<{result: any}>;
  sendRaw(options: { data: string, client: number }): Promise<{result: any}>;
}

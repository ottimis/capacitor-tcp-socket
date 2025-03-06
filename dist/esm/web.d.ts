import { WebPlugin } from '@capacitor/core';
import { SocketTCPPlugin } from './definitions';
export declare class SocketTCPWeb extends WebPlugin implements SocketTCPPlugin {
    NOT_SUPPORTED: string;
    constructor();
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    connect(_options: {
        ipAddress: string;
        port: number;
    }): Promise<any>;
    disconnect(_options: {
        client: number;
    }): Promise<any>;
    send(_options: {
        data: string;
    }): Promise<any>;
    sendRaw(_options: {
        data: string;
    }): Promise<any>;
}

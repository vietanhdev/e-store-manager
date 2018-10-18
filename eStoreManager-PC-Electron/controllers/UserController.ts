export class UserController {

    private token: string;
    
    public static isLoggedIn() {
        return false;
    }

    // Return data:
    // {
    //     success: boolean;
    //     errorCode: number;
    //     message: string;
    //     token: string;
    // }
    public login(username: string, password: string):any {

        return Object({
            success: false,
            errorCode: 100,
            message: "Wrong username or password",
            token: "asjdasui32d902d233d"
        });

    }
 
}

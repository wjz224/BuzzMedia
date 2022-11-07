import { cleanup, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import LoginPage from './LoginPage'

//Testing file for HomePage.tsx

//Clears the buffer after each test
afterEach(() => { 
  cleanup();
})

//1 test included in below description
describe("Homepage Component" , () => {

  test('Render LoginPage', () => {
    render(<LoginPage />);
    const linkElement = screen.getByText('Buzz');
    expect(linkElement).toBeInTheDocument();
    fetch(`https://thebuzzomega.herokuapp.com/verify/eyJhbGciOiJSUzI1NiIsImtpZCI6Ijc3Y2MwZWY0YzcxODFjZjRjMGRjZWY3YjYwYWUyOGNjOTAyMmM3NmIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMzM4Njk3NTgyNTYtcm02M2pndWhpMmljZnZwdjA3Y2NzNm0wZGFjbm51aGouYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIzMzg2OTc1ODI1Ni1ybTYzamd1aGkyaWNmdnB2MDdjY3M2bTBkYWNubnVoai5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwODE5NDgyOTc2MDUxODE2ODUzOSIsImhkIjoibGVoaWdoLmVkdSIsImVtYWlsIjoidG1hMjI0QGxlaGlnaC5lZHUiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6IjBBXzBycnVZY2M0aWNPUjVzbEF2TEEiLCJuYW1lIjoiVGlhbmEgQWxkcm91YmkiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUxtNXd1M2o2N1RVdlpyNWpYRjFDZVNRc2dQTDRKbTE3Mi1nQm1ENnZJc2o9czk2LWMiLCJnaXZlbl9uYW1lIjoiVGlhbmEiLCJmYW1pbHlfbmFtZSI6IkFsZHJvdWJpIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE2NjcxODI2MTgsImV4cCI6MTY2NzE4NjIxOCwianRpIjoiMDE2NDI1OTMyNzFiMWYwY2VlYzFlNDFhOTZhNTRmMTlmNDJiNGM3YiJ9.pNxMV5q3NQVde6nOPTx3uBnjrErAz0mk8uxSB3476bVvj_LOjQfkKj2c4n0PkiUxegLLj01mu4jRvwVLyOpc_ndhMB_DhrjyvIROXByOj5DZVye6IDLPZgeAjIP0omRhZCE7GP6rmS3TNPRG2Hv4OojqiHQmyjF_ncbAjNyHEjhxw1LhirxDvtoRJZrAbG7nYz11azln4ZUjLLJdCQ71_gkhkiUJ-GS-Gaump8JU46ttnNMZ-t8qCdtcI6Cf9uiGWSZoH-ieENeYhpNxO34ru8eAvwCuTfmCLz12JhMANDvHdpqZXnjq2VnQ_dwDzvtvJQqZpGKOxQdiC1GkM4T2ZA`,
    {
        method: "POST",
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
        mode: "cors",
    })
    .then((response) => response.json())
    .then((data) => {
        // setMessages(data.mData)
        console.log(data)
        //navigate('/profile', {  state: {sessionId: data.mData}})
        //navigate('/profile', {  state: {sessionId: data.mData, email: email, familyName: familyName, givenName: givenName, googleId: googleId, imageUrl: imageUrl, name: name}})
        expect(data.res).toBe(200)

    });
  }) 
  
})
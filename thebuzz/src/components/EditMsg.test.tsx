import React from 'react';
import { cleanup, getByTestId, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"

afterEach(() => { 
  cleanup();
})

describe("EditMsg Component" , () => {

  test("Update Button Rendering", () => {
    expect(screen.queryByTestId('updatebtn')).toBeEnabled()
  })

  test("Update Button Text", () => {
    expect(screen.queryByTestId('updatebtn')).toHaveTextContent("Update")
  })  
})
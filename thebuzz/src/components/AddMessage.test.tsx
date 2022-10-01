import React from 'react';
import { cleanup, getByTestId, render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';
import "@testing-library/jest-dom/extend-expect"
import AddMessage from './AddMessage';

afterEach(() => { 
  cleanup();
})

describe("AddMessage Component" , () => {

  test("Add Button Rendering", () => {
    expect(screen.queryByTestId("addbtn")).toBeEnabled()
  })

  test("Add Button Text", () => {
    expect(screen.queryByTestId("addbtn")).toHaveTextContent("Add")
  })  
})